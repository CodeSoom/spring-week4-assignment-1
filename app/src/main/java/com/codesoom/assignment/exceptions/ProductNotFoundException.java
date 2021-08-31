package com.codesoom.assignment.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public static final String FORMAT = "식별자 %s에 해당하는 상품을 찾을 수 없습니다.";

    public ProductNotFoundException(long id) {
        super(FORMAT.formatted(id));
    }
}
