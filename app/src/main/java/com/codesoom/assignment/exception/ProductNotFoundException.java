package com.codesoom.assignment.exception;

public final class ProductNotFoundException extends IllegalStateException {

    private static final String MESSAGE = "상픔을 찾을 수 없습니다.";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
