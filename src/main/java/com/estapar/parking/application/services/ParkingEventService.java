package com.estapar.parking.application.services;

import com.estapar.parking.domain.model.ParkingEvent;
import com.estapar.parking.domain.model.Sector;
import com.estapar.parking.domain.repository.ParkingEventRepository;
import com.estapar.parking.domain.repository.SectorRepository;
import com.estapar.parking.interfaces.dto.request.WebhookEventRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ParkingEventService {

    private final ParkingEventRepository parkingEventRepository;
    private final SectorRepository sectorRepository;

    public void registerEntry(WebhookEventRequest event) {
        Sector sector = findAvailableSector();
        parkingEventRepository.register(new ParkingEvent(event.licensePlate(), event.entryTime(), sector));
    }

    public void registerExit(WebhookEventRequest event) {
        ParkingEvent parkingEvent = parkingEventRepository
                .findFirstByLicensePlateAndExitTimeIsNullOrderByEntryTimeDesc(event.licensePlate())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado na garagem"));

        BigDecimal price = calculatePrice(parkingEvent);
        parkingEventRepository.register(new ParkingEvent(parkingEvent.getId(), event.exitTime(), price));
    }

    private BigDecimal calculatePrice(ParkingEvent event) {
        final long periodDuration = 15;
        Duration duration = Duration.between(event.getEntryTime(), event.getExitTime());
        long minutes = duration.toMinutes();

        if (minutes <= periodDuration) return BigDecimal.ZERO;

        Sector sector = event.getSector();
        BigDecimal base = sector.getBasePrice();

        BigDecimal multiplier = getPriceMultiplier(sector);
        base = base.multiply(multiplier);

        long billableMinutes = minutes - periodDuration;
        BigDecimal firstHour = base;
        BigDecimal afterFirstHour = base.multiply(BigDecimal.valueOf(billableMinutes - 60).max(BigDecimal.ZERO))
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.UP);

        return firstHour.add(afterFirstHour.max(BigDecimal.ZERO));
    }

    private BigDecimal getPriceMultiplier(Sector sector) {
        long total = sector.getMaxCapacity();
        long occupied = parkingEventRepository.countOccupiedBySector(sector.getId());

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

