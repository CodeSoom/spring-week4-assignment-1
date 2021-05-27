package com.codesoom.assignment;

/**
 * 상품을 찾을 수 없을 때 예외
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super("Product Not Found: " + productId);
    }
}
