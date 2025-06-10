package com.estapar.parking.application.usecases;

import com.estapar.parking.interfaces.dto.request.WebhookEventRequest;
import com.estapar.parking.application.services.ParkingEventService;
import com.estapar.parking.application.services.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebhookUseCase {

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

