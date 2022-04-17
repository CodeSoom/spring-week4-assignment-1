package com.codesoom.assignment.dto;

/**
 * 예외 메세지를 담는 객체
* */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
