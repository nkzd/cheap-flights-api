package io.vukotic.flightadvisor.persistence.repository;

import io.vukotic.flightadvisor.persistence.model.City;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends CrudRepository<City, Long> {
    List<City> findByNameContainingIgnoreCase(String name);

    Optional<City> findCityByNameAndCountry(String name, String country);

    List<City> findAll();

}
