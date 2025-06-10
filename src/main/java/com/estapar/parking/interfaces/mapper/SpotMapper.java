package com.estapar.parking.interfaces.mapper;

import com.estapar.parking.domain.model.Spot;
import com.estapar.parking.infrastructures.entity.SpotEntity;
import com.estapar.parking.interfaces.dto.response.SpotStatusResponse;
import java.time.OffsetDateTime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpotMapper {
    Spot toDomain(SpotEntity entity);
    SpotEntity toEntity(Spot spot);
    SpotStatusResponse toSpotStatusResponse(boolean ocupied, OffsetDateTime entryTime, OffsetDateTime timeParked);
}
