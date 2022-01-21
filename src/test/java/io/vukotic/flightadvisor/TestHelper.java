package io.vukotic.flightadvisor;

import io.vukotic.flightadvisor.persistence.model.Role;
import io.vukotic.flightadvisor.persistence.repository.CityRepository;
import io.vukotic.flightadvisor.persistence.repository.CommentRepository;
import io.vukotic.flightadvisor.persistence.repository.RoleRepository;
import io.vukotic.flightadvisor.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestHelper {
    private final CommentRepository commentRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public TestHelper(CommentRepository commentRepository, CityRepository cityRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.commentRepository = commentRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void clearDatabase() {
        commentRepository.deleteAll();
        cityRepository.deleteAll();
        userRepository.deleteAll();
        addRole();
    }

    private void addRole() {
        roleRepository.deleteAll();
        var role = new Role();
        role.setId(1L);
        role.setName("ROLE_REGULAR_USER");
        roleRepository.save(role);
    }
}
