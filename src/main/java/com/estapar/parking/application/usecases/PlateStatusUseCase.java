package com.estapar.parking.application.usecases;

import com.estapar.parking.domain.model.ParkingEvent;
import com.estapar.parking.application.services.ParkingService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlateStatusUseCase {

    private final ParkingService service;

    public Optional<ParkingEvent> execute(final String licensePlate) {
        return service.getPlateStatus(licensePlate);
    }
}
