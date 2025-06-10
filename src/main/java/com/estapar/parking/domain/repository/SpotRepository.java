package com.estapar.parking.domain.repository;

import com.estapar.parking.domain.model.Spot;
import java.util.List;
import java.util.Optional;

public interface SpotRepository {
    Long countSpot();
    List<Spot> saveSpots(final List<Spot> spotEntities);
    Optional<Spot> findByLatAndLng(final Double lat, final Double lng);
}
