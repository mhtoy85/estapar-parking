package com.estapar.parking.dto.response;

public record SpotResponse(
        long id,
        String sector,
        double lat,
        double lng
) {
}
