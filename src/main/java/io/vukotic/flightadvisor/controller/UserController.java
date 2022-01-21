package io.vukotic.flightadvisor.controller;

import io.vukotic.flightadvisor.persistence.model.User;
import io.vukotic.flightadvisor.service.JWTTokenService;
import io.vukotic.flightadvisor.service.UserService;
import io.vukotic.flightadvisor.dto.LoginDto;
import io.vukotic.flightadvisor.dto.UserDto;
import io.vukotic.flightadvisor.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final JWTTokenService jwtTokenService;

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String login(@Valid @RequestBody LoginDto login) {
        return jwtTokenService.createJwtToken(login);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDto registerUser(@Valid @RequestBody UserDto user) {
        User userEntity = userMapper.convertUserToEntity(user);
        return userMapper.convertUserToDto(userService.registerUser(userEntity));
    }
}
