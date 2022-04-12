package com.codesoom.assignment;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("CatToy not found: " + id);
    }
}
