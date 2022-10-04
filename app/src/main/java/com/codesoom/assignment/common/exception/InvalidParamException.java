package com.codesoom.assignment.common.exception;

/**
 * 상품 필수항목인데 입력이 되지 않은 항목이 있을경우 발생하는 예외
 */
public class InvalidParamException extends RuntimeException {
    public InvalidParamException() {
        super("입력이 되지않은 항목이 있습니다.");
    }
    public InvalidParamException(String errorMsg) {
        super(errorMsg);
    }
}
