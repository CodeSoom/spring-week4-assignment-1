package com.codesoom.assignment.product.exception;

public class ProductNotFoundException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "식별자가 [%d]인 제품을 찾을 수 없었습니다.";

    public ProductNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
