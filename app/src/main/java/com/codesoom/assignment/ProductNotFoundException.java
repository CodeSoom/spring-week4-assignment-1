package com.codesoom.assignment;

/**
 * 상품을 찾을 수 없는 경우에 던집니다.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super("Product Not Found: " + productId);
    }
}
