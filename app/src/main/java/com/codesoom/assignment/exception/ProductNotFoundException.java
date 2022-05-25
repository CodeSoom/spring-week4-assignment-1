package com.codesoom.assignment.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException() {
        super("상품이 없습니다.");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
