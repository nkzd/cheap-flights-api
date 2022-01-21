package io.vukotic.flightadvisor.persistence.repository;

import io.vukotic.flightadvisor.persistence.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
