package com.estapar.parking.interfaces.dto.response;

public record SpotResponse(
        long id,
        String sector,
        double lat,
        double lng
) {
}
