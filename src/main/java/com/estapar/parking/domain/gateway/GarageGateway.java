package com.estapar.parking.domain.gateway;

import com.estapar.parking.interfaces.dto.response.GarageResponse;

public interface GarageGateway {
    GarageResponse getGarageConfig();
}
