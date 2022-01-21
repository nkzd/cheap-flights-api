package io.vukotic.flightadvisor.controller;

import com.google.gson.Gson;
import io.vukotic.flightadvisor.TestHelper;
import io.vukotic.flightadvisor.dto.LoginDto;
import io.vukotic.flightadvisor.dto.UserDto;
import io.vukotic.flightadvisor.error.exception.UserNotFoundException;
import io.vukotic.flightadvisor.persistence.model.Role;
import io.vukotic.flightadvisor.persistence.repository.RoleRepository;
import io.vukotic.flightadvisor.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final TestHelper testHelper;
    private final UserRepository userRepository;
    private final MockMvc mvc;

    @Autowired
    public UserControllerTest(TestHelper testHelper, UserRepository userRepository, MockMvc mvc) {
        this.testHelper = testHelper;
        this.userRepository = userRepository;
        this.mvc = mvc;
    }


    @BeforeEach
    public void clear() {
        testHelper.clearDatabase();
    }

    @Test
    public void givenDefaultUsersPersisted_whenRequestLogin_thenReturnToken() throws Exception {
        var userDto = new UserDto();
        userDto.setUsername("test@test.com");
        userDto.setPassword("testtest");
        userDto.setFirstName("test");
        userDto.setLastName("test");
        var gson = new Gson();

        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("test")))
                .andExpect(jsonPath("$.lastName", is("test")))
                .andExpect(jsonPath("$.username", is("test@test.com")));


        var loginDto = new LoginDto();
        loginDto.setUsername("test@test.com");
        loginDto.setPassword("testtest");

        mvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(String.class)));
    }

    @Test
    public void whenRequestRegister_thenReturnUser() throws Exception {
        var userDto = new UserDto();
        userDto.setUsername("test@test.com");
        userDto.setPassword("testtest");
        userDto.setFirstName("test");
        userDto.setLastName("test");
        Gson gson = new Gson();
        String json = gson.toJson(userDto);

        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("test")))
                .andExpect(jsonPath("$.lastName", is("test")))
                .andExpect(jsonPath("$.username", is("test@test.com")));
    }

    @Test
    public void givenDefaultUsersPersisted_whenRequestingUserIdThatDoesntExist_thenThrowUserNotFoundException() throws Exception {
        userRepository.deleteAll();
        var loginDto = new LoginDto();
        loginDto.setUsername("wrong user");
        loginDto.setPassword("wrong user");
        Gson gson = new Gson();
        String json = gson.toJson(loginDto);

        mvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));

    }

}
