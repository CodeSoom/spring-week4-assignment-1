package com.codesoom.assignment.dto;

/**
 * 에러 메세지 응답객체 입니다.
 */
public class ErrorResponse {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
