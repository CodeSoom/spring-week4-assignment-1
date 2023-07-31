package com.codesoom.assignment.product.application;

import com.codesoom.assignment.common.BaseException;

public class InvalidProductRequest extends BaseException {
    public static final String MESSAGE = "유효하지 않은 상품 정보입니다";

    public InvalidProductRequest() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
