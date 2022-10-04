package com.codesoom.assignment.exception;

/**
 * 장난감을 찾지 못했을 때 발생하는 예외
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
