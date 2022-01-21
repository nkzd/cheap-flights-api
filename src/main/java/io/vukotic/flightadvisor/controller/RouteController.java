package io.vukotic.flightadvisor.controller;

import io.vukotic.flightadvisor.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void importRoutes(@RequestParam MultipartFile file) {
        routeService.importRoutes(file);
    }

}