package com.estapar.parking.interfaces.dto.request;

import java.time.LocalDate;

public record RevenueRequest(LocalDate date, String sector) {}
