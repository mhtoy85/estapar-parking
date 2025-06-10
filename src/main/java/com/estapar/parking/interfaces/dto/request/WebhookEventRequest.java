package com.estapar.parking.interfaces.dto.request;

import java.time.OffsetDateTime;

public record WebhookEventRequest(
        String licensePlate,
        OffsetDateTime entryTime,
        OffsetDateTime exitTime,
        double lat,
        double lng,
        String eventType
) {
}
