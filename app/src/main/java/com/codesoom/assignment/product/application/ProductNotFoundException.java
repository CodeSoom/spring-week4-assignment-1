package com.codesoom.assignment.product.application;

import com.codesoom.assignment.common.baseException;

public class ProductNotFoundException extends baseException {
    public static final String MESSAGE = "해당하는 상품이 존재하지 않습니다";

    public ProductNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
