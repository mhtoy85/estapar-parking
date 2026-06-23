package com.estapar.parking.application.services;

import com.estapar.parking.domain.model.ParkingEvent;
import com.estapar.parking.domain.repository.ParkingEventRepository;
import com.estapar.parking.domain.repository.SpotRepository;
import com.estapar.parking.interfaces.dto.request.WebhookEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SpotService {

    private final SpotRepository spotRepository;
    private final ParkingEventRepository parkingEventRepository;

    public void registerParking(final WebhookEventRequest event) {
        var spot = spotRepository.findByLatAndLng(event.lat(), event.lng())
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada"));

        ParkingEvent parkingEvent = parkingEventRepository
                .findFirstByLicensePlateAndExitTimeIsNullOrderByEntryTimeDesc(event.licensePlate())
                .orElseThrow(() -> new RuntimeException("Evento de entrada não encontrado"));
        parkingEventRepository.register(new ParkingEvent(parkingEvent.getId(), spot));
    }
}

