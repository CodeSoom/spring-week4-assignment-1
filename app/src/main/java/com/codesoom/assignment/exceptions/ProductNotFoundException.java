package com.codesoom.assignment.exceptions;

/**
 * 상품을 찾지 못한 경우 던집니다.
 */
public class ProductNotFoundException extends RuntimeException {

    private static final String FORMAT = "식별자 %s에 해당하는 상품을 찾을 수 없습니다.";

    /**
     * 식별자를 통해 상품을 찾지 못할 경우 발생합니다.
     *
     * @param id 식별자
     */
    public ProductNotFoundException(long id) {
        super(String.format(FORMAT, id));
    }
}
