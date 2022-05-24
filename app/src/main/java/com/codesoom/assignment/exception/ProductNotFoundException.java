package com.codesoom.assignment.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(id + "는 존재하지 않습니다.");
    }
}
