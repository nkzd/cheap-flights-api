package io.vukotic.flightadvisor.service;

import com.opencsv.bean.CsvToBeanBuilder;
import io.vukotic.flightadvisor.persistence.model.Airport;
import io.vukotic.flightadvisor.persistence.model.Route;
import io.vukotic.flightadvisor.persistence.repository.AirportRepository;
import io.vukotic.flightadvisor.persistence.repository.RouteRepository;
import io.vukotic.flightadvisor.dto.RouteDto;
import io.vukotic.flightadvisor.error.exception.CorruptedFileException;
import io.vukotic.flightadvisor.mapper.RouteMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;
    private final AirportRepository airportRepository;

    public void importRoutes(MultipartFile file) {

        List<RouteDto> routesDtos;

        try {
            routesDtos = new CsvToBeanBuilder<RouteDto>(new BufferedReader(new InputStreamReader(file.getInputStream())))
                    .withType(RouteDto.class)
                    .withThrowExceptions(false)
                    .build()
                    .parse();
        } catch (IOException ex) {
            throw new CorruptedFileException();
        }

        Map<Long, Airport> availableAirportMap = airportRepository.findAll()
                .stream().collect(Collectors.toMap(Airport::getAirportId, airport -> airport));

        RouteMapper routeMapper = new RouteMapper(modelMapper, availableAirportMap);
        List<Route> routeEntities = routesDtos
                .parallelStream()
                .map(routeMapper::convertRouteToEntity)
                .filter(Objects::nonNull)
                .filter(this::validateRouteFields)
                .collect(Collectors.toList());
        routeRepository.saveAll(routeEntities);
    }

    private boolean validateRouteFields(Route route) {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(route);
        return violations.isEmpty();
    }
}
