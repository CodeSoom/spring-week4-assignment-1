package com.codesoom.assignment.common.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("요청하신 상품이 없습니다.[ID:" + id + "]");
    }
}
