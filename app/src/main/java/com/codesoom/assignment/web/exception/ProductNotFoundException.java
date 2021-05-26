package com.codesoom.assignment.web.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("The product not found.");
    }
}
