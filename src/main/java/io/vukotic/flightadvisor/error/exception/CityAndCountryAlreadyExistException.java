package io.vukotic.flightadvisor.error.exception;

public class CityAndCountryAlreadyExistException extends RuntimeException {

    public CityAndCountryAlreadyExistException() {
        super("City in this country already exists");
    }
}
