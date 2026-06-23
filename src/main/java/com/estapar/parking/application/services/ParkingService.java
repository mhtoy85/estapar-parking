package com.estapar.parking.application.services;

import com.estapar.parking.domain.model.ParkingEvent;
import com.estapar.parking.domain.model.Sector;
import com.estapar.parking.domain.model.Spot;
import com.estapar.parking.domain.repository.ParkingEventRepository;
import com.estapar.parking.domain.repository.SectorRepository;
import com.estapar.parking.domain.repository.SpotRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

    @Transactional(readOnly = true)
    public Optional<ParkingEvent> getPlateStatus(final String licensePlate) {
        return parkingEventRepository.findActiveByLicensePlate(licensePlate);
    }

    @Transactional(readOnly = true)
    public Optional<Spot> getSpotStatus(double lat, double lng) {
        return spotRepository.findByLatAndLng(lat, lng);
    }

    @Transactional(readOnly = true)
    public BigDecimal getRevenueByDateAndSector(final LocalDate date, final String sectorName) {
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


