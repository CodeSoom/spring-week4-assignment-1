package com.codesoom.assignment.controller.dto;

/**
 * 예외를 던질 경우 응답 형식을 통일시키기 위함
 */
public class ErrorResponse {

    private String code;
    private String message;

    private ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
