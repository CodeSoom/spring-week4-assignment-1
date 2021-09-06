package com.codesoom.assignment.dto;

public final class ErrorResponseDto {
    private final String url;
    private final String method;
    private final String error;


    public ErrorResponseDto(
        final String url, final String method, final String error
        ) {
        this.url = url;
        this.method = method;
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public String getError() {
        return error;
    }

    public String getMethod() {
        return method;
    }
}
