package com.codesoom.assignment.product.ui.dto;

/**
 * 에러 메시지 응답.
 */
public class ErrorResponse {
    final private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
