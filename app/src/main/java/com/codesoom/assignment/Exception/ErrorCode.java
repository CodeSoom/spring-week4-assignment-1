package com.codesoom.assignment.Exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    PRODUCT_NOT_FOUND("Product Id is not found");

    private final String description;
    ErrorCode(String description) {
        this.description = description;
    }
}
