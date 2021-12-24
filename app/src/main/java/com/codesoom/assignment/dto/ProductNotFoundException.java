package com.codesoom.assignment.dto;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Task not found: " + id);
    }
}
