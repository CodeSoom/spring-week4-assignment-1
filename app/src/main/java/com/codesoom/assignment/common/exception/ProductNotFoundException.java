package com.codesoom.assignment.common.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
