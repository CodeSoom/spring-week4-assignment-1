package com.codesoom.assignment;

/**
 * Product를 찾을수 없는경우
 */
public final class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(final Long id) {
        super("Product not found: " + id);
    }
}
