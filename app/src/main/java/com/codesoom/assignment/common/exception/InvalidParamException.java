package com.codesoom.assignment.common.exception;

public class InvalidParamException extends RuntimeException {
    public InvalidParamException() {
        super("입력이 되지않은 항목이 있습니다.");
    }
    public InvalidParamException(String errorMsg) {
        super(errorMsg);
    }
}
