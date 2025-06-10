package com.estapar.parking.infrastructures.repository;


import com.estapar.parking.domain.model.ParkingEvent;
import com.estapar.parking.domain.model.Sector;
import com.estapar.parking.domain.repository.ParkingEventRepository;
import com.estapar.parking.infrastructures.entity.ParkingEventEntity;
import com.estapar.parking.interfaces.mapper.ParkingEventMapper;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingEventRepositoryImpl implements ParkingEventRepository {

    private final SpringDataParkingEventRepository springDataParkingEventRepository;
    private final ParkingEventMapper parkingEventMapper;

    @Override
    public Optional<ParkingEvent> findFirstByLicensePlateAndExitTimeIsNullOrderByEntryTimeDesc(final String plate) {
        Optional<ParkingEventEntity> optionalParkingEventEntity = springDataParkingEventRepository
                .findFirstByLicensePlateAndExitTimeIsNullOrderByEntryTimeDesc(plate);
        return optionalParkingEventEntity.map(parkingEventMapper::toDomain);
    }

    @Override
    public long countOccupiedBySector(final Long sectorId) {
        return springDataParkingEventRepository.countOccupiedBySector(sectorId);
    }

    @Override
    public Optional<ParkingEvent> findActiveByLicensePlate(final String licensePlate) {
        Optional<ParkingEventEntity> optionalParkingEventEntity = springDataParkingEventRepository
                .findActiveByLicensePlate(licensePlate);
        return optionalParkingEventEntity.map(parkingEventMapper::toDomain);
    }

    @Override
    public BigDecimal sumRevenueBySectorAndExitTimeBetween(final Sector sector,
                                                           final OffsetDateTime start,
                                                           final OffsetDateTime end) {
        return springDataParkingEventRepository.sumRevenueBySectorAndExitTimeBetween(sector.getId(), start, end);
    }

    @Override
    public void register(final ParkingEvent parkingEvent) {
        springDataParkingEventRepository.save(parkingEventMapper.toEntity(parkingEvent));
    }
}
