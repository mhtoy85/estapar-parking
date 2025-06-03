package com.estapar.parking.service;

import com.estapar.parking.dto.request.WebhookEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WebhookService {

    private final ParkingEventService parkingEventService;
    private final SpotService spotService;

    public void processEvent(final WebhookEventRequest event) {
        switch (event.eventType()) {
            case "ENTRY" -> parkingEventService.registerEntry(event);
            case "EXIT" -> parkingEventService.registerExit(event);
            case "PARKED" -> spotService.registerParking(event);
            default -> throw new IllegalArgumentException("Tipo de evento desconhecido: " + event.eventType());
        }
    }
}

