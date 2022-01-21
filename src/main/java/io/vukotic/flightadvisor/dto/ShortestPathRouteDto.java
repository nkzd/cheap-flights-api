package io.vukotic.flightadvisor.dto;

import lombok.Data;

@Data
public class ShortestPathRouteDto {
    private final String sourceCityName;
    private final String destinationCityName;
    private final Double routePrice;
}
