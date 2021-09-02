package com.codesoom.assignment.product.exception;

public class ProductInvalidFieldException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "필드 정보를 공백 혹은 Null으로 수정할 수 없습니다.";

    public ProductInvalidFieldException() {
        super(DEFAULT_MESSAGE);
    }
}
