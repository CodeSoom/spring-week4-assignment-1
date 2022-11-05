package com.codesoom.assignment.exception.products;

import com.codesoom.assignment.exception.CommonException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CommonException {
    private static final String MESSAGE = "상품이 존재하지 않습니다.";

    /**
     * 상품을 찾지 못했을 때 던집니다.
     */
    public ProductNotFoundException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}
