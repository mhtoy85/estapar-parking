package com.estapar.parking.dto.request;

import java.time.LocalDate;

public record RevenueRequest(LocalDate date, String sector) {}
