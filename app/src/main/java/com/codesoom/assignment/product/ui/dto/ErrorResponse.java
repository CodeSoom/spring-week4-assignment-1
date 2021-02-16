package com.codesoom.assignment.product.ui.dto;

public class ErrorResponse {
    final private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
