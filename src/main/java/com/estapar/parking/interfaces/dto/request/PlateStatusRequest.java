package com.estapar.parking.interfaces.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PlateStatusRequest(@NotBlank String license_plate) {}