package com.codesoom.assignment.common.exception;

/**
 * 상품이 존재하지 않을 때 예외를 정의한다
 * */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
