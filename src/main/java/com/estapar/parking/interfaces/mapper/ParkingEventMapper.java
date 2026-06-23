package com.estapar.parking.interfaces.mapper;

import com.estapar.parking.domain.model.ParkingEvent;
import com.estapar.parking.infrastructures.entity.ParkingEventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParkingEventMapper {
    ParkingEvent toDomain(ParkingEventEntity entity);
    ParkingEventEntity toEntity(ParkingEvent parkingEvent);
}
