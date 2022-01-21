package io.vukotic.flightadvisor.controller;

import com.google.gson.Gson;
import io.vukotic.flightadvisor.dto.CityDto;
import io.vukotic.flightadvisor.persistence.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerTest {
    private final MockMvc mvc;
    private final CityRepository cityRepository;

    @Autowired
    public CityControllerTest(MockMvc mvc, CityRepository cityRepository) {
        this.mvc = mvc;
        this.cityRepository = cityRepository;
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void whenRequestCreateCity_thenReturnCreatedCity() throws Exception {
        cityRepository.deleteAll();
        var cityDto = new CityDto();
        cityDto.setCountry("test");
        cityDto.setDescription("test");
        cityDto.setName("test");
        var json = new Gson().toJson(cityDto);

        mvc.perform(post("/cities/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country", is("test")))
                .andExpect(jsonPath("$.description", is("test")))
                .andExpect(jsonPath("$.name", is("test")));
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = {"REGULAR_USER", "ADMIN"})
    public void givenCityIsCreated_whenRequestCitySearch_thenReturnCreatedCity() throws Exception {
        cityRepository.deleteAll();
        var cityDto = new CityDto();
        cityDto.setCountry("test");
        cityDto.setDescription("test");
        cityDto.setName("test");
        var json = new Gson().toJson(cityDto);
        mvc.perform(post("/cities/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mvc.perform(get("/cities?cityName=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name", contains("test")));
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = {"REGULAR_USER", "ADMIN"})
    public void givenNoCities_whenRequestCitySearch_thenReturnEmptyArray() throws Exception {
        cityRepository.deleteAll();
        var result = mvc.perform(get("/cities?cityName=test"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result, "[]");
    }


}
