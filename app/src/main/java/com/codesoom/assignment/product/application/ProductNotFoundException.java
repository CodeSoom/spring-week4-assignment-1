package com.codesoom.assignment.product.application;

/**
 * 상품 예외 처리
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
