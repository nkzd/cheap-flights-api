package io.vukotic.flightadvisor.service;

import io.vukotic.flightadvisor.persistence.model.City;
import io.vukotic.flightadvisor.persistence.repository.CityRepository;
import io.vukotic.flightadvisor.persistence.repository.CommentRepository;
import io.vukotic.flightadvisor.error.exception.CityAndCountryAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final CommentRepository commentRepository;

    public City createCity(City city) {
        cityRepository.findCityByNameAndCountry(city.getName(), city.getCountry()).ifPresent((c) ->
        {
            throw new CityAndCountryAlreadyExistException();
        });
        return cityRepository.save(city);
    }

    public List<City> findCities(String cityName, Integer commentCount) {
        var cities = (cityName != null) ? cityRepository.findByNameContainingIgnoreCase(cityName) : cityRepository.findAll();

        if (commentCount != null && commentCount > 0) {
            cities.forEach(city -> {
                Pageable commentCountPageRequest = PageRequest.of(0, commentCount, Sort.by("createdAt").descending());
                var comments = commentRepository.findByCity(city, commentCountPageRequest);
                city.setComments(comments);
            });
        }

        return cities;

    }

}
