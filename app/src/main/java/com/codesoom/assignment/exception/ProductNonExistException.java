package com.codesoom.assignment.exception;

public class ProductNonExistException extends RuntimeException {
    ProductNonExistException(String msg) {
        super(msg);
    }
}
