package com.estapar.parking.service;

import com.estapar.parking.model.Sector;
import com.estapar.parking.model.ParkingEvent;
import com.estapar.parking.model.Spot;
import com.estapar.parking.repository.SectorRepository;
import com.estapar.parking.repository.ParkingEventRepository;
import com.estapar.parking.repository.SpotRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ParkingService {

    private final SectorRepository sectorRepository;
    private final SpotRepository spotRepository;
    private final ParkingEventRepository parkingEventRepository;

    @Transactional
    public void handleEntryEvent(String licensePlate, OffsetDateTime entryTime) {
        // Buscar setores abertos no horário da entrada
        List<Sector> openSectors = sectorRepository.findAll(); // ideal filtrar por horário aberto

        // Verificar lotação por setor
        for (Sector sector : openSectors) {
            int parkedCount = parkingEventRepository.countActiveBySector(sector);
            if (parkedCount >= sector.getMaxCapacity()) {
                continue; // setor cheio, tenta outro
            }

            // Checagem ok: criar novo ParkingEvent sem preço ainda
            ParkingEvent event = new ParkingEvent();
            event.setLicensePlate(licensePlate);
            event.setEntryTime(entryTime);
            event.setSector(sector);
            parkingEventRepository.save(event);
            return; // já cadastrado, sai
        }

        throw new RuntimeException("Todos os setores estão com lotação máxima.");
    }

    @Transactional
    public void handleParkedEvent(String licensePlate, double lat, double lng) {
        Optional<ParkingEvent> optEvent = parkingEventRepository.findActiveByLicensePlate(licensePlate);
        if (optEvent.isEmpty()) {
            throw new RuntimeException("Evento de estacionamento não encontrado para placa: " + licensePlate);
        }
        ParkingEvent event = optEvent.get();

        Optional<Spot> spotOpt = spotRepository.findByLatAndLng(lat, lng);
        if (spotOpt.isEmpty()) {
            throw new RuntimeException("Vaga não encontrada para lat/lng: " + lat + ", " + lng);
        }
        Spot spot = spotOpt.get();

        event.setSpot(spot);
        parkingEventRepository.save(event);
    }

    @Transactional
    public BigDecimal handleExitEvent(String licensePlate, OffsetDateTime exitTime) {
        Optional<ParkingEvent> optEvent = parkingEventRepository.findActiveByLicensePlate(licensePlate);
        if (optEvent.isEmpty()) {
            throw new RuntimeException("Evento de estacionamento ativo não encontrado para placa: " + licensePlate);
        }
        ParkingEvent event = optEvent.get();

        event.setExitTime(exitTime);

        BigDecimal price = calculatePrice(event.getEntryTime(), exitTime, event.getSector().getBasePrice());
        event.setPrice(price);

        parkingEventRepository.save(event);

        return price;
    }

    private BigDecimal calculatePrice(OffsetDateTime entryTime, OffsetDateTime exitTime, BigDecimal basePrice) {
        Duration duration = Duration.between(entryTime, exitTime);
        long totalMinutes = duration.toMinutes();

        if (totalMinutes <= 15) {
            return BigDecimal.ZERO.setScale(2);
        }

        // 1ª hora = 100%
        // após 15 min, próximo período cobrado pro-rata em períodos de 15 min

        long chargeableMinutes = totalMinutes - 15;

        BigDecimal price = BigDecimal.ZERO;

        if (chargeableMinutes <= 60) {
            price = basePrice;
        } else {
            price = basePrice;

            long extraMinutes = chargeableMinutes - 60;
            // Calcula número de blocos de 15 minutos extras, arredondando para cima
            long blocks = (extraMinutes + 14) / 15;

            BigDecimal blockPrice = basePrice.divide(BigDecimal.valueOf(4), 2, RoundingMode.HALF_UP);
            price = price.add(blockPrice.multiply(BigDecimal.valueOf(blocks)));
        }

        return price.setScale(2, RoundingMode.HALF_UP);
    }

    @Transactional(readOnly = true)
    public Optional<ParkingEvent> getPlateStatus(String licensePlate) {
        return parkingEventRepository.findActiveByLicensePlate(licensePlate);
    }

    @Transactional(readOnly = true)
    public Optional<Spot> getSpotStatus(double lat, double lng) {
        return spotRepository.findByLatAndLng(lat, lng);
    }

    @Transactional(readOnly = true)
    public BigDecimal getRevenueByDateAndSector(LocalDate date, String sectorName) {
        Optional<Sector> sectorOpt = sectorRepository.findByName(sectorName);
        if (sectorOpt.isEmpty()) {
            throw new RuntimeException("Setor não encontrado: " + sectorName);
        }
        Sector sector = sectorOpt.get();

        OffsetDateTime startOfDay = date.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
        OffsetDateTime endOfDay = date.plusDays(1).atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();

        BigDecimal totalRevenue = parkingEventRepository.sumRevenueBySectorAndExitTimeBetween(sector, startOfDay, endOfDay);
        return totalRevenue == null ? BigDecimal.ZERO : totalRevenue;
    }

}


