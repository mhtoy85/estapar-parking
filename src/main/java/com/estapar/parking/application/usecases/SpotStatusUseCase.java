package com.estapar.parking.application.usecases;

import com.estapar.parking.application.services.ParkingService;
import com.estapar.parking.domain.model.Spot;
import com.estapar.parking.interfaces.dto.response.SpotStatusResponse;
import com.estapar.parking.interfaces.mapper.SpotMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotStatusUseCase {

    private final ParkingService service;
    private final SpotMapper spotMapper;

    public SpotStatusResponse execute(final double lat, final double lng) {
        Optional<Spot> spotStatus = service.getSpotStatus(lat, lng);
        return null;
    }
}
