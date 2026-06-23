package com.estapar.parking.interfaces.dto.response;

import java.math.BigDecimal;

public record GarageSectorResponse(
        String sector,
        BigDecimal basePrice,
        int max_capacity,
        String open_hour,
        String close_hour,
        int duration_limit_minutes
) {}
