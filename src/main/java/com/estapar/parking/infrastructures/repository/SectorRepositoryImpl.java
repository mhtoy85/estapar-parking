package com.estapar.parking.infrastructures.repository;

import com.estapar.parking.domain.model.Sector;
import com.estapar.parking.domain.repository.SectorRepository;
import com.estapar.parking.infrastructures.entity.SectorEntity;
import com.estapar.parking.interfaces.mapper.SectorMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SectorRepositoryImpl implements SectorRepository {

    private final SpringDataSectorRepository springDataSectorRepository;
    private final SectorMapper sectorMapper;

    @Override
    public Long countSector() {
        return springDataSectorRepository.count();
    }

    @Override
    public List<Sector> saveSectors(List<Sector> sectorEntities) {
        List<SectorEntity> list = sectorEntities.stream().map(sectorMapper::toEntity).toList();
        return springDataSectorRepository.saveAll(list).stream().map(sectorMapper::toDomain).toList();
    }

    @Override
    public Optional<Sector> findByName(final String name) {
        return springDataSectorRepository.findByName(name)
                .map(sectorMapper::toDomain);
    }

    @Override
    public Optional<Sector> findFirstWithAvailableSpots() {
        return springDataSectorRepository.findFirstWithAvailableSpots().map(sectorMapper::toDomain);
    }
}
