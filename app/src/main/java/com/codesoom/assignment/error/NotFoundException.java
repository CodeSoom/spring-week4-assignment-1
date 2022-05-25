package com.codesoom.assignment.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
