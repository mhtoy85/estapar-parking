package com.estapar.parking.domain.repository;

import com.estapar.parking.domain.model.Sector;
import java.util.List;
import java.util.Optional;

public interface SectorRepository {
    Long countSector();
    List<Sector> saveSectors(final List<Sector> sectorEntities);
    Optional<Sector> findByName(final String name);
    Optional<Sector> findFirstWithAvailableSpots();
}
