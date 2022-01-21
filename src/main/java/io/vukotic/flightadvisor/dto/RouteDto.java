package io.vukotic.flightadvisor.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class RouteDto {
    @CsvBindByPosition(position = 0)
    private String airlineCode;
    @CsvBindByPosition(position = 1)
    private Long airlineId;
    @CsvBindByPosition(position = 2)
    private String sourceAirport;
    @CsvBindByPosition(position = 3)
    private Long sourceAirportId;
    @CsvBindByPosition(position = 4)
    private String destinationAirport;
    @CsvBindByPosition(position = 5)
    private Long destinationAirportId;
    @CsvBindByPosition(position = 6)
    private Boolean codeShare;
    @CsvBindByPosition(position = 7)
    private Integer stops;
    @CsvBindByPosition(position = 8)
    private String equipment;
    @CsvBindByPosition(position = 9)
    private Double price;
}
