package com.estapar.parking.domain.repository;

import com.estapar.parking.domain.model.ParkingEvent;
import com.estapar.parking.domain.model.Sector;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface ParkingEventRepository {
    Optional<ParkingEvent> findFirstByLicensePlateAndExitTimeIsNullOrderByEntryTimeDesc(final String plate);
    long countOccupiedBySector(final Long sectorId);
    Optional<ParkingEvent> findActiveByLicensePlate(final String licensePlate);
    BigDecimal sumRevenueBySectorAndExitTimeBetween(final Sector sector,
                                                    final OffsetDateTime start,
                                                    final OffsetDateTime end);
    void register(final ParkingEvent parkingEvent);
}
