package io.vukotic.flightadvisor.service;

import io.vukotic.flightadvisor.persistence.model.User;
import io.vukotic.flightadvisor.persistence.repository.RoleRepository;
import io.vukotic.flightadvisor.persistence.repository.UserRepository;
import io.vukotic.flightadvisor.error.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        userRepository.findUserByUsername(user.getUsername()).ifPresent((u) -> {
            throw new UsernameAlreadyExistsException();
        });
        var role = roleRepository.findRoleByName("ROLE_REGULAR_USER");
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
