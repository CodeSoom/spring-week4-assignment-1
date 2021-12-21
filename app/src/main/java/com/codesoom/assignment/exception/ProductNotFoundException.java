package com.codesoom.assignment.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
