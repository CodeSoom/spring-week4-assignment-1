package com.codesoom.assignment.application;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long id) {
        super(String.format("%s에 해당하는 상품을 찾을 수 없습니다.", id));
    }

}
