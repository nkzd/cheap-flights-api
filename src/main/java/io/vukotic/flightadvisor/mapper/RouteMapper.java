package io.vukotic.flightadvisor.mapper;

import io.vukotic.flightadvisor.persistence.model.Airport;
import io.vukotic.flightadvisor.persistence.model.Route;
import io.vukotic.flightadvisor.dto.RouteDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Map;

@RequiredArgsConstructor
public class RouteMapper {
    private final ModelMapper modelMapper;
    private final Map<Long, Airport> airportMap;

    public Route convertRouteToEntity(RouteDto routeDto) {
        var sourceAirportEntity = airportMap.get(routeDto.getSourceAirportId());
        if (sourceAirportEntity == null) return null;
        var destinationAirportEntity = airportMap.get(routeDto.getDestinationAirportId());
        if (destinationAirportEntity == null) return null;

        modelMapper
                .typeMap(RouteDto.class, Route.class)
                .addMappings(mapper -> mapper.skip(Route::setSourceAirport))
                .addMappings(mapper -> mapper.skip(Route::setDestinationAirport));
        var routeEntity = modelMapper.map(routeDto, Route.class);
        routeEntity.setSourceAirport(sourceAirportEntity);
        routeEntity.setDestinationAirport(destinationAirportEntity);
        return routeEntity;
    }
}

