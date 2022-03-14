package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;

    @Builder
    public ErrorResponse(String message) {
        this.message = message;
    }
}
