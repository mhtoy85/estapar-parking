package com.estapar.parking.infrastructures.repository;

import com.estapar.parking.infrastructures.entity.SectorEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSectorRepository extends JpaRepository<SectorEntity, Long> {
    Optional<SectorEntity> findByName(String name);

    @Query("""
        SELECT s FROM Sector s
        WHERE s.maxCapacity > (
            SELECT COUNT(e)
            FROM ParkingEvent e
            WHERE e.sector = s AND e.exitTime IS NULL
        )
        """)
    Optional<SectorEntity> findFirstWithAvailableSpots();
}
