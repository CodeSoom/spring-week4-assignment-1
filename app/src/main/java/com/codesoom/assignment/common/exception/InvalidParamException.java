package com.codesoom.assignment.common.exception;

public class InvalidParamException extends RuntimeException {
    public InvalidParamException(String errorMsg) {
        super(errorMsg);
    }
}
