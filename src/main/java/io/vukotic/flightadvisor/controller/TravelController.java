package io.vukotic.flightadvisor.controller;

import io.vukotic.flightadvisor.service.TravelService;
import io.vukotic.flightadvisor.dto.ShortestPathDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/travel")
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;

    @GetMapping("/{sourceCityId}/{destinationCityId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ShortestPathDto findCheapestFlight(@PathVariable Long sourceCityId,
                                              @PathVariable Long destinationCityId) {
        return travelService.findCheapestFlight(sourceCityId,destinationCityId);
    }
}
