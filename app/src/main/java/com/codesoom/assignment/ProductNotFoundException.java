package com.codesoom.assignment;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long productId) {
        super("Product Not Found: " + productId);
    }
}
