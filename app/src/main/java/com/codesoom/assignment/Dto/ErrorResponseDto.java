package com.codesoom.assignment.Dto;

public final class ErrorResponseDto {
    private final String error;

    public ErrorResponseDto(final String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
