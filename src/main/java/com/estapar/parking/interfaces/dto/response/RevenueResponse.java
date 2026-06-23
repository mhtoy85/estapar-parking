package com.estapar.parking.interfaces.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record RevenueResponse(
    BigDecimal amount,
    String currency,
    OffsetDateTime timestamp) {
}
