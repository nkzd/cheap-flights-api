package io.vukotic.flightadvisor.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShortestPathDto {
    private final Double totalPrice;
    private final Integer length;
    private final List<ShortestPathRouteDto> shortestPathRoutes;
}
