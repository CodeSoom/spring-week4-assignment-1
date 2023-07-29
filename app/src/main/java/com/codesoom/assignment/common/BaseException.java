package com.codesoom.assignment.common;

import com.codesoom.assignment.common.dto.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseException extends RuntimeException {
    public final List<ErrorValidation> errors = new ArrayList<>();

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

    public void addValidation(String source, String type, String message) {
        errors.add(new ErrorValidation(source, type, message));
    }

    public ErrorResponse toErrorResponse() {
        return new ErrorResponse(
                String.valueOf(getStatusCode()),
                getMessage(),
                errors
        );
    }
}
