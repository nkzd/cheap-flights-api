package io.vukotic.flightadvisor.error.exception;

public class JWTException extends RuntimeException {
    public JWTException() {
        super("Invalid JWT token");
    }
}
