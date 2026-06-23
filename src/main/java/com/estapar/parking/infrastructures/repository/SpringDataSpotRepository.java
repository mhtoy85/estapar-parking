package com.estapar.parking.infrastructures.repository;

import com.estapar.parking.infrastructures.entity.SpotEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSpotRepository extends JpaRepository<SpotEntity, Long> {
    Optional<SpotEntity> findByLatAndLng(final Double lat, final Double lng);
}
