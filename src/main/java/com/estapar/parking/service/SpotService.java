package com.estapar.parking.service;

import com.estapar.parking.dto.request.WebhookEventRequest;
import com.estapar.parking.model.ParkingEvent;
import com.estapar.parking.repository.ParkingEventRepository;
import com.estapar.parking.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SpotService {

    private final SpotRepository spotRepository;
    private final ParkingEventRepository eventRepository;

    public void registerParking(final WebhookEventRequest event) {
        var spot = spotRepository.findByLatAndLng(event.lat(), event.lng())
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada"));

        ParkingEvent parkingEvent = eventRepository
                .findFirstByLicensePlateAndExitTimeIsNullOrderByEntryTimeDesc(event.licensePlate())
                .orElseThrow(() -> new RuntimeException("Evento de entrada não encontrado"));

        parkingEvent.setSpot(spot);
        eventRepository.save(parkingEvent);
    }
}

