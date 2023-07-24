package com.codesoom.assignment.common;

import com.codesoom.assignment.common.dto.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class baseException extends RuntimeException {
    public final Map<String, String> validation = new HashMap<>();

    public baseException() {
    }

    public baseException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

    public ErrorResponse toErrorResponse() {
        return new ErrorResponse(
                String.valueOf(getStatusCode()),
                getMessage(),
                validation
        );
    }
}
