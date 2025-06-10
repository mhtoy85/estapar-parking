package com.estapar.parking.interfaces.mapper;

import com.estapar.parking.domain.model.Sector;
import com.estapar.parking.infrastructures.entity.SectorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SectorMapper {
    Sector toDomain(SectorEntity entity);
    SectorEntity toEntity(Sector sector);
}
