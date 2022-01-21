package io.vukotic.flightadvisor.controller;

import io.vukotic.flightadvisor.service.CityService;
import io.vukotic.flightadvisor.dto.CityDto;
import io.vukotic.flightadvisor.mapper.CityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CityDto createCity(@Valid @RequestBody CityDto city) {
        var cityEntity = cityMapper.convertCityToEntity(city);
        return cityMapper.convertCityToDto(cityService.createCity(cityEntity));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CityDto> findCities(
            @RequestParam(required = false, defaultValue = "") String cityName,
            @RequestParam(required = false, defaultValue = "100") Integer commentCount
    ) {
        var cities = cityService.findCities(cityName, commentCount);
        return cities.stream().map(cityMapper::convertCityToDto).collect(Collectors.toList());
    }
}
