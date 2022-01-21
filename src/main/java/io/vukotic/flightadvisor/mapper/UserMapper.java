package io.vukotic.flightadvisor.mapper;

import io.vukotic.flightadvisor.persistence.model.User;
import io.vukotic.flightadvisor.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public User convertUserToEntity(UserDto user) {
        return modelMapper.map(user, User.class);
    }

    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
