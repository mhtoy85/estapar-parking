package com.estapar.parking.application.usecases;

import com.estapar.parking.application.services.ParkingService;
import com.estapar.parking.interfaces.dto.response.RevenueResponse;
import com.estapar.parking.interfaces.mapper.RevenueMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RevenueUseCase {

    private final static String CURRENCY = "BRL";
    private final ParkingService parkingService;
    private final RevenueMapper revenueMapper;

    public RevenueResponse execute(final LocalDate date, final String sectorName) {
        BigDecimal amount = parkingService.getRevenueByDateAndSector(date, sectorName);
        return revenueMapper.toRevenueResponse(amount, CURRENCY, OffsetDateTime.now());
    }
}
