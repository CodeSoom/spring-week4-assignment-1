package com.codesoom.assignment.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("There is no product number" + id);

    }
}
