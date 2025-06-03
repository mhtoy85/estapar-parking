package com.estapar.parking.repository;

import com.estapar.parking.model.Sector;
import com.estapar.parking.model.ParkingEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParkingEventRepository extends JpaRepository<ParkingEvent, Long> {
    Optional<ParkingEvent> findFirstByLicensePlateAndExitTimeIsNullOrderByEntryTimeDesc(String plate);

    @Query("SELECT COUNT(e) FROM ParkingEvent e WHERE e.sector.id = :sectorId AND e.exitTime IS NULL")
    long countOccupiedBySector(@Param("sectorId") Long sectorId);

    @Query("SELECT SUM(e.price) FROM ParkingEvent e WHERE e.sector.name = :sector AND CAST(e.exitTime AS date) = :date")
    BigDecimal getRevenue(@Param("sector") String sector, @Param("date") LocalDate date);

    // Conta quantos veículos estão ativos (sem exitTime) no setor
    @Query("SELECT COUNT(pe) FROM ParkingEvent pe WHERE pe.sector = :sector AND pe.exitTime IS NULL")
    int countActiveBySector(@Param("sector") Sector sector);

    // Busca evento ativo (sem exitTime) pela placa
    @Query("SELECT pe FROM ParkingEvent pe WHERE pe.licensePlate = :licensePlate AND pe.exitTime IS NULL")
    Optional<ParkingEvent> findActiveByLicensePlate(@Param("licensePlate") String licensePlate);

    // Soma faturamento por setor entre datas (considerando somente eventos com exitTime dentro do intervalo)
    @Query("SELECT SUM(pe.price) FROM ParkingEvent pe WHERE pe.sector = :sector AND pe.exitTime BETWEEN :start AND :end")
    BigDecimal sumRevenueBySectorAndExitTimeBetween(@Param("sector") Sector sector,
                                                    @Param("start") OffsetDateTime start,
                                                    @Param("end") OffsetDateTime end);
}
