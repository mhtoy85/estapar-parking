package com.estapar.parking.interfaces.dto.webhook;

import java.time.Instant;

public record WebhookEvent(
        String license_plate,
        Double lat,
        Double lng,
        String event_type,
        Instant entry_time,
        Instant exit_time
) {}

