package io.vukotic.flightadvisor.controller;

import io.vukotic.flightadvisor.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void importAirports(@RequestParam MultipartFile file) {
        airportService.importAirports(file);
    }

}