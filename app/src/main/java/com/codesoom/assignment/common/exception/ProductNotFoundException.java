package com.codesoom.assignment.common.exception;

/**
 * 상품 ID에 해당하는 상품정보를 찾을수 없을때 발생하는 예외
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("요청하신 상품이 없습니다.[ID:" + id + "]");
    }
}
