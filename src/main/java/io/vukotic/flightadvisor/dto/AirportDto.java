package io.vukotic.flightadvisor.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class AirportDto {
    @CsvBindByPosition(position = 0, required = true)
    private Long airportId;
    @CsvBindByPosition(position = 1, required = true)
    private String name;
    @CsvBindByPosition(position = 2, required = true)
    private String city;
    @CsvBindByPosition(position = 3, required = true)
    private String country;
    @CsvBindByPosition(position = 4, required = true)
    private String iata;
    @CsvBindByPosition(position = 5, required = true)
    private String icao;
    @CsvBindByPosition(position = 6, required = true)
    private Double latitude;
    @CsvBindByPosition(position = 7, required = true)
    private Double longitude;
    @CsvBindByPosition(position = 8, required = true)
    private Integer altitude;
    @CsvBindByPosition(position = 9, required = true)
    private Double timezone;
    @CsvBindByPosition(position = 10, required = true)
    private String dst;
    @CsvBindByPosition(position = 11, required = true)
    private String tz;
    @CsvBindByPosition(position = 12, required = true)
    private String type;
    @CsvBindByPosition(position = 13, required = true)
    private String dataSource;
}
