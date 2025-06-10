package com.estapar.parking.adapters.gateways;

import com.estapar.parking.interfaces.dto.response.GarageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "garage-client", url = "${garage.client.url}")
public interface GarageClient {

    @GetMapping
    GarageResponse getGarageConfig();
}
