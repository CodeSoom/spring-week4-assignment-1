package com.codesoom.assignment;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("product not found: " + id);
    }
}

