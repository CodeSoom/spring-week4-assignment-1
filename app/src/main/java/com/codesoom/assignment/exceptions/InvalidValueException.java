package com.codesoom.assignment.exceptions;

public class InvalidValueException extends RuntimeException {

    private static final String MESSAGE = "Not Proper Value";

    public InvalidValueException() {
        super(MESSAGE);
    }

}
