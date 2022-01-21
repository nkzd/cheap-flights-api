package io.vukotic.flightadvisor.error.exception;

public class NoFlightPathException extends RuntimeException {
    public NoFlightPathException() {
        super("Flight path between cities doesn't exist");
    }
}
