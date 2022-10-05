package com.codesoom.assignment.exception;

/**
 * 상품을 찾지 못했을 때 던집니다.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
