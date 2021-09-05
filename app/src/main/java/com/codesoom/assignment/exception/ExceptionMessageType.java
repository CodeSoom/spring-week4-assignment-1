package com.codesoom.assignment.exception;

public enum ExceptionMessageType {
    GET("조회 요청"),
    POST("추가 요청"),
    PUT("수정 요청"),
    DELETE("삭제 요청");

    private final String message;

    ExceptionMessageType(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
