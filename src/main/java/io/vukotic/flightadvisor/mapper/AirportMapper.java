package io.vukotic.flightadvisor.mapper;

import io.vukotic.flightadvisor.persistence.model.Airport;
import io.vukotic.flightadvisor.persistence.model.City;
import io.vukotic.flightadvisor.dto.AirportDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@RequiredArgsConstructor
public class AirportMapper {
    private final ModelMapper modelMapper;
    private final List<City> availableCities;

    public Airport convertAirportToEntity(AirportDto airportDto) {
        var cityEntity = getAvailableCity(airportDto.getCity(), airportDto.getCountry());
        if (cityEntity == null) return null;
        modelMapper
                .typeMap(AirportDto.class, Airport.class)
                .addMappings(mapper -> mapper.skip(Airport::setCity));
        var airportEntity = modelMapper.map(airportDto, Airport.class);
        airportEntity.setCity(cityEntity);
        return airportEntity;
    }


    public City getAvailableCity(String cityName, String countryName) {
        return availableCities
                .stream()
                .filter(city -> (city.getName().equals(cityName) && city.getCountry().equals(countryName)))
                .findAny()
                .orElse(null);
    }
}

