package com.estapar.parking.repository;

import com.estapar.parking.model.Sector;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    @Query("SELECT s FROM Sector s WHERE s.maxCapacity > " +
            "(SELECT COUNT(e) FROM ParkingEvent e WHERE e.sector = s AND e.exitTime IS NULL)")
    Optional<Sector> findFirstWithAvailableSpots();

    Optional<Sector> findByName(String name);

}
