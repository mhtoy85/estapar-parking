package com.estapar.parking.controller;

import com.estapar.parking.dto.request.PlateStatusRequest;
import com.estapar.parking.dto.request.RevenueRequest;
import com.estapar.parking.dto.request.SpotStatusRequest;
import com.estapar.parking.service.ParkingService;
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

    private final ParkingService service;

    @PostMapping("/plate-status")
    public ResponseEntity<?> plateStatus(@RequestBody PlateStatusRequest request) {
        return ResponseEntity.ok(service.getPlateStatus(request.license_plate()));
    }

    @PostMapping("/spot-status")
    public ResponseEntity<?> spotStatus(@RequestBody SpotStatusRequest request) {
        return ResponseEntity.ok(service.getSpotStatus(request.lat(), request.lng()));
    }

    @GetMapping("/revenue")
    public ResponseEntity<?> revenue(@RequestBody RevenueRequest request) {
        return ResponseEntity.ok(service.getRevenueByDateAndSector(request.date(), request.sector()));
    }
}

