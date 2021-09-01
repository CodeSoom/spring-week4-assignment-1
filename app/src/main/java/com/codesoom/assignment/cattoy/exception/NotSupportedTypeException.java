package com.codesoom.assignment.cattoy.exception;

public class NotSupportedTypeException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "유효하지 않은 타입입니다. 지원 타입: [%s] , 입력 타입: [%s]";

    public NotSupportedTypeException(String sourceName, String targetName) {
        super(String.format(DEFAULT_MESSAGE, sourceName, targetName));
    }
}
