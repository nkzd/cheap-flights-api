package io.vukotic.flightadvisor.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Airport {

    @Id
    private Long airportId;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @NotNull
    @Size(min = 1, max = 100)
    private String country;

    @NotNull
    @Size(min = 3, max = 3)
    private String iata;

    @NotNull
    @Size(min = 4, max = 4)
    private String icao;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private Integer altitude;

    private Double timezone;

    @NotNull
    private Dst dst;

    @NotNull
    @Size(min = 1, max = 100)
    private String tz;

    @NotNull
    @Size(min = 1, max = 100)
    private String type;

    @NotNull
    @Size(min = 1, max = 100)
    private String dataSource;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;

        Airport airport = (Airport) o;

        return getAirportId().equals(airport.getAirportId());
    }

    @Override
    public int hashCode() {
        return getAirportId().hashCode();
    }

    public enum Dst {
        E, A, S, O, Z, N, U
    }

}
