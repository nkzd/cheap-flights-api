package io.vukotic.flightadvisor.mapper;

import io.vukotic.flightadvisor.persistence.model.City;
import io.vukotic.flightadvisor.dto.CityDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityMapper {
    private final ModelMapper modelMapper;

    public City convertCityToEntity(CityDto city) {
        return modelMapper.map(city, City.class);
    }

    public CityDto convertCityToDto(City city) {
        return modelMapper.map(city, CityDto.class);
    }
}
