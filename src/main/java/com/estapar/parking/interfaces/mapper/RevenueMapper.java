package com.estapar.parking.interfaces.mapper;

import com.estapar.parking.interfaces.dto.response.RevenueResponse;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RevenueMapper {

    RevenueResponse toRevenueResponse(BigDecimal amount, String currency, OffsetDateTime timestamp);
}
