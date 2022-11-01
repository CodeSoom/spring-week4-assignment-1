package com.codesoom.assignment.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(id + "에 해당하는 제품이 없습니다.");
    }
}
