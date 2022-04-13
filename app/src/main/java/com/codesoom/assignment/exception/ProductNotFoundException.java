package com.codesoom.assignment.exception;

/**
 * 상품을 찾을 수 없을 때 던집니다.
 */
public final class ProductNotFoundException extends IllegalStateException {

    private static final String MESSAGE = "상픔을 찾을 수 없습니다.";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
