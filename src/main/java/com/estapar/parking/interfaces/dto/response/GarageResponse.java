package com.estapar.parking.interfaces.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class GarageResponse {
    private List<GarageSectorResponse> garage;
    private List<SpotResponse> spots;
}
