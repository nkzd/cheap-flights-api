package io.vukotic.flightadvisor.persistence.repository;

import io.vukotic.flightadvisor.persistence.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);

    List<Role> findAll();
}
