package com.estapar.parking.service;

import com.estapar.parking.dto.response.GarageResponse;
import com.estapar.parking.model.Sector;
import com.estapar.parking.model.Spot;
import com.estapar.parking.repository.SectorRepository;
import com.estapar.parking.repository.SpotRepository;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GarageSetupService implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final SectorRepository sectorRepository;
    private final SpotRepository spotRepository;

    @Override
    public void run(String... args) throws Exception {
        String url = "http://localhost:3000/garage";

        GarageResponse response = restTemplate.getForObject(url, GarageResponse.class);

        if (response != null) {
            if (sectorRepository.count() == 0) {
                sectorRepository.saveAll(response.getGarage().stream().map(g -> {
                    Sector s = new Sector();
                    s.setName(g.sector());
                    s.setBasePrice(Objects.isNull(g.basePrice()) ? BigDecimal.ZERO : g.basePrice());
                    s.setMaxCapacity(g.max_capacity());
                    s.setOpenHour(LocalTime.parse(g.open_hour()));
                    s.setCloseHour(LocalTime.parse(g.close_hour()));
                    s.setDurationLimitMinutes(g.duration_limit_minutes());
                    return s;
                }).toList());
            }

            if (spotRepository.count() == 0) {
                spotRepository.saveAll(response.getSpots().stream().map(s -> {
                    Optional<Sector> optionalSector = sectorRepository.findByName(s.sector());
                    if (optionalSector.isPresent()) {
                        Spot ps = new Spot();
                        ps.setLat(s.lat());
                        ps.setLng(s.lng());
                        ps.setSector(optionalSector.get());
                        return ps;
                    }
                   return null;
                }).filter(Objects::nonNull).toList());
            }

            log.info("Garagem inicializada com sucesso.");
        } else {
            log.error("❌ Falha ao obter dados da garagem.");
        }
    }
}

