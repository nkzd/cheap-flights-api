package io.vukotic.flightadvisor.error.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException() {
        super("City not found");
    }
}
