package com.codesoom.assignment.Exception;

import lombok.Getter;


public enum ErrorCode {
    PRODUCT_NOT_FOUND("Product Id is not found");

    public String getDescription() {
        return description;
    }

    private final String description;
    ErrorCode(String description) {
        this.description = description;
    }
}
