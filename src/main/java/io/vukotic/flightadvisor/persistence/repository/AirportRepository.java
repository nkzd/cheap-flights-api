package io.vukotic.flightadvisor.persistence.repository;

import io.vukotic.flightadvisor.persistence.model.Airport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AirportRepository extends CrudRepository<Airport, Long> {
    List<Airport> findAll();
}
