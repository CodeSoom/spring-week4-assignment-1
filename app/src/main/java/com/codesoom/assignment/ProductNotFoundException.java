package com.codesoom.assignment;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Task not found:" + id);
    }
}
