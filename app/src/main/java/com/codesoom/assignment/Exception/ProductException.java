package com.codesoom.assignment.Exception;

import lombok.Builder;

/**
 * A custom exception class for the product
 */
public class ProductException extends Exception {

    private ErrorCode errorCode;

    private final String message;

    public ProductException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;

    }

}
