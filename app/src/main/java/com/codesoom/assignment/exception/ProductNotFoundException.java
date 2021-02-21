package com.codesoom.assignment.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("상품이 존재하지 않습니다.");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

}
