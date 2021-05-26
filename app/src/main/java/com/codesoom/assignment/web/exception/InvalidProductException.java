package com.codesoom.assignment.web.exception;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException() {
        super("The product's required items do not exist.");
    }
}
