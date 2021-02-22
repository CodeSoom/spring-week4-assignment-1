package com.codesoom.assignment.exception;

public class ProductNonExistException extends RuntimeException {
    public ProductNonExistException(Long id) {
        super("Product not found: " + id);
    }
}
