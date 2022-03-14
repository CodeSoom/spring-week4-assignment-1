package com.codesoom.assignment.errors;

/**
 * 상품을 찾지 못하면 예외를 던집니다.
 */
public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message) {
        super(message);
    }
}
