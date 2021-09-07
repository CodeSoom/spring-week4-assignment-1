package com.codesoom.assignment.eception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(long id) {
        super("Product not found: " + id);
    }
}
