package com.estapar.parking.interfaces.dto.request;

import jakarta.validation.constraints.NotNull;

public record SpotStatusRequest(@NotNull Double lat, @NotNull Double lng) {}
