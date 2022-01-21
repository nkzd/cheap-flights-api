package io.vukotic.flightadvisor.error.handler;

import lombok.Data;

@Data
public class FieldViolation {
    private final String fieldName;
    private final String message;
}
