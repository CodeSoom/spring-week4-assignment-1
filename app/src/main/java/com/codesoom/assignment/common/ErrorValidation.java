package com.codesoom.assignment.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorValidation {
    private final String source;
    private final String type;
    private final String message;

    @Builder
    public ErrorValidation(String source, String type, String message) {
        this.source = source;
        this.type = type;
        this.message = message;
    }
}
