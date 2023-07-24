package com.codesoom.assignment.product.application;

public class ProductNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당하는 상품이 존재하지 않습니다";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
