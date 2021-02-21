package com.codesoom.assignment;

public class ProductNotFountException extends RuntimeException {
    public ProductNotFountException(Long id) {
        super("Product not found: " + id);
    }
}
