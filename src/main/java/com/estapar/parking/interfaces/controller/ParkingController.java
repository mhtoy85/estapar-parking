package com.estapar.parking.interfaces.controller;

import com.estapar.parking.application.usecases.PlateStatusUseCase;
import com.estapar.parking.application.usecases.RevenueUseCase;
import com.estapar.parking.application.usecases.SpotStatusUseCase;
import com.estapar.parking.interfaces.dto.request.PlateStatusRequest;
import com.estapar.parking.interfaces.dto.request.RevenueRequest;
import com.estapar.parking.interfaces.dto.request.SpotStatusRequest;
import com.estapar.parking.interfaces.dto.response.RevenueResponse;
import com.estapar.parking.interfaces.dto.response.SpotStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ParkingController {

    private final PlateStatusUseCase plateStatusUseCase;
    private final SpotStatusUseCase spotStatusUseCase;
    private final RevenueUseCase revenueUseCase;

    @PostMapping("/plate-status")
    public ResponseEntity<?> plateStatus(@RequestBody final PlateStatusRequest request) {
        return ResponseEntity.ok(plateStatusUseCase.execute(request.license_plate()));
    }

    @PostMapping("/spot-status")
    public ResponseEntity<SpotStatusResponse> spotStatus(@RequestBody final SpotStatusRequest request) {
        return ResponseEntity.ok(spotStatusUseCase.execute(request.lat(), request.lng()));
    }

    @GetMapping("/revenue")
    public ResponseEntity<RevenueResponse> revenue(final RevenueRequest request) {
        return ResponseEntity.ok(revenueUseCase.execute(request.date(), request.sector()));
    }
}

