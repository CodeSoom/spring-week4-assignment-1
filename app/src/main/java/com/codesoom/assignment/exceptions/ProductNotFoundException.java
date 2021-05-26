package com.codesoom.assignment.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(String.format("Not found product(id: %d)", id));
    }
}
