package com.codesoom.assignment;

public final class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(final Long id) {
        super("Product not found: " + id);
    }
}
