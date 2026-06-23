package com.estapar.parking.adapters.gateways;

import com.estapar.parking.domain.gateway.GarageGateway;
import com.estapar.parking.interfaces.dto.response.GarageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GarageClientAdapter implements GarageGateway {

    private final GarageClient client;

    @Override
    public GarageResponse getGarageConfig() {
        return client.getGarageConfig();
    }
}
