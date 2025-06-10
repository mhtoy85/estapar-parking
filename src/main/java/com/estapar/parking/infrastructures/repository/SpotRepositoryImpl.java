package com.estapar.parking.infrastructures.repository;

import com.estapar.parking.domain.model.Spot;
import com.estapar.parking.domain.repository.SpotRepository;
import com.estapar.parking.infrastructures.entity.SpotEntity;
import com.estapar.parking.interfaces.mapper.SpotMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotRepositoryImpl implements SpotRepository {

    private final SpringDataSpotRepository springDataSpotRepository;
    private final SpotMapper spotMapper;

    @Override
    public Long countSpot() {
        return springDataSpotRepository.count();
    }

    @Override
    public List<Spot> saveSpots(final List<Spot> spotEntities) {
        List<SpotEntity> list = spotEntities.stream().map(spotMapper::toEntity).toList();
        return springDataSpotRepository.saveAll(list).stream().map(spotMapper::toDomain).toList();
    }

    @Override
    public Optional<Spot> findByLatAndLng(final Double lat, final Double lng) {
        return springDataSpotRepository.findByLatAndLng(lat, lng).map(spotMapper::toDomain);
    }
}
