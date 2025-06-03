package com.estapar.parking.service;

import com.estapar.parking.dto.request.WebhookEventRequest;
import com.estapar.parking.model.Sector;
import com.estapar.parking.model.ParkingEvent;
import com.estapar.parking.repository.SectorRepository;
import com.estapar.parking.repository.ParkingEventRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ParkingEventService {

    private final ParkingEventRepository eventRepository;
    private final SectorRepository sectorRepository;

    public void registerEntry(WebhookEventRequest event) {
        Sector sector = findAvailableSector();
        ParkingEvent parkingEvent = new ParkingEvent();
        parkingEvent.setLicensePlate(event.licensePlate());
        parkingEvent.setEntryTime(event.entryTime());
        parkingEvent.setSector(sector);

        eventRepository.save(parkingEvent);
    }

    public void registerExit(WebhookEventRequest event) {
        ParkingEvent parkingEvent = eventRepository
                .findFirstByLicensePlateAndExitTimeIsNullOrderByEntryTimeDesc(event.licensePlate())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado na garagem"));

        parkingEvent.setExitTime(event.exitTime());

        BigDecimal price = calculatePrice(parkingEvent);
        parkingEvent.setPrice(price);

        eventRepository.save(parkingEvent);
    }

    private BigDecimal calculatePrice(ParkingEvent event) {
        Duration duration = Duration.between(event.getEntryTime(), event.getExitTime());
        long minutes = duration.toMinutes();

        if (minutes <= 15) return BigDecimal.ZERO;

        Sector sector = event.getSector();
        BigDecimal base = sector.getBasePrice();

        BigDecimal multiplier = getPriceMultiplier(sector);
        base = base.multiply(multiplier);

        long billableMinutes = minutes - 15;
        BigDecimal firstHour = base;
        BigDecimal afterFirstHour = base.multiply(BigDecimal.valueOf(billableMinutes - 60).max(BigDecimal.ZERO))
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.UP);

        return firstHour.add(afterFirstHour.max(BigDecimal.ZERO));
    }

    private BigDecimal getPriceMultiplier(Sector sector) {
        long total = sector.getMaxCapacity();
        long occupied = eventRepository.countOccupiedBySector(sector.getId());

        double ratio = (double) occupied / total;

        if (ratio < 0.25) return BigDecimal.valueOf(0.90);
        else if (ratio < 0.50) return BigDecimal.valueOf(1.00);
        else if (ratio < 0.75) return BigDecimal.valueOf(1.10);
        else return BigDecimal.valueOf(1.25);
    }

    private Sector findAvailableSector() {
        return sectorRepository.findFirstWithAvailableSpots()
                .orElseThrow(() -> new IllegalStateException("Nenhum setor disponível"));
    }
}

