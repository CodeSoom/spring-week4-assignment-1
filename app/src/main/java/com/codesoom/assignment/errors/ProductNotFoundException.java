package com.codesoom.assignment.errors;

/**
 * 주어진 id의 상품을 찾지 못하면 예외를 던진다.
 */
public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message) {
        super(message);
    }
}
