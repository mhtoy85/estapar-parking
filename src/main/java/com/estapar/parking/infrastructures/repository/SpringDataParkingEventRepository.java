package com.estapar.parking.infrastructures.repository;

import com.estapar.parking.infrastructures.entity.ParkingEventEntity;
import com.estapar.parking.infrastructures.entity.SectorEntity;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataParkingEventRepository extends JpaRepository<ParkingEventEntity, Long> {
    Optional<ParkingEventEntity> findFirstByLicensePlateAndExitTimeIsNullOrderByEntryTimeDesc(String plate);

    @Query("SELECT COUNT(e) FROM ParkingEvent e WHERE e.sector.id = :sectorId AND e.exitTime IS NULL")
    long countOccupiedBySector(@Param("sectorId") Long sectorId);

    @Query("SELECT COUNT(pe) FROM ParkingEvent pe WHERE pe.sector.id = :sectorId AND pe.exitTime IS NULL")
    int countActiveBySector(@Param("sectorId") Long sectorId);

    @Query("SELECT pe FROM ParkingEvent pe WHERE pe.licensePlate = :licensePlate AND pe.exitTime IS NULL")
    Optional<ParkingEventEntity> findActiveByLicensePlate(@Param("licensePlate") String licensePlate);

    @Query("SELECT SUM(pe.price) FROM ParkingEvent pe WHERE pe.sector.id = :sectorId AND pe.exitTime BETWEEN :start AND :end")
    BigDecimal sumRevenueBySectorAndExitTimeBetween(@Param("sectorId") Long id,
                                                    @Param("start") OffsetDateTime start,
                                                    @Param("end") OffsetDateTime end);
}
