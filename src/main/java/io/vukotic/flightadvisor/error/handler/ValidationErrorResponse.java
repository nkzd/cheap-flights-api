package io.vukotic.flightadvisor.error.handler;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse {
    private final List<FieldViolation> fieldViolations = new ArrayList<>();
}
