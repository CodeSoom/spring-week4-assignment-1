package com.codesoom.assignment.common;

import com.codesoom.assignment.common.dto.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseException extends RuntimeException {
    public final Map<String, String> validation = new HashMap<>();

    public BaseException() {
    }

    public BaseException(String message) {
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
