package com.estapar.parking.application.services;

import com.estapar.parking.domain.gateway.GarageGateway;
import com.estapar.parking.domain.model.Sector;
import com.estapar.parking.domain.model.Spot;
import com.estapar.parking.domain.repository.SectorRepository;
import com.estapar.parking.domain.repository.SpotRepository;
import com.estapar.parking.interfaces.dto.response.GarageResponse;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GarageSetupService implements CommandLineRunner {

    private final GarageGateway garageGateway;
    private final SectorRepository sectorRepository;
    private final SpotRepository spotRepository;

    @Override
    public void run(String... args) throws Exception {
        GarageResponse response = garageGateway.getGarageConfig();

        if (response != null) {
            if (sectorRepository.countSector() == 0) {
                sectorRepository.saveSectors(response.getGarage().stream().map(garage -> {
                    final BigDecimal basePrice = Objects.isNull(garage.basePrice()) ? BigDecimal.ZERO : garage.basePrice();
                    return new Sector(garage.sector(), basePrice, garage.max_capacity(), LocalTime.parse(garage.open_hour()),
                            LocalTime.parse(garage.close_hour()), garage.duration_limit_minutes());
                }).toList());
            }

            if (spotRepository.countSpot() == 0) {
                spotRepository.saveSpots(response.getSpots().stream().map(sector -> {
                    Optional<Sector> optionalSector = sectorRepository.findByName(sector.sector());
                    return optionalSector.map(value -> new Spot(sector.lat(), sector.lng(), value))
                            .orElse(null);
                }).filter(Objects::nonNull).toList());
            }

            log.info("Garagem inicializada com sucesso.");
        } else {
            log.error("❌ Falha ao obter dados da garagem.");
        }
    }
}

