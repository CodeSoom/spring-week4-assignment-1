package com.codesoom.assignment.common.exception;

public class NotFoundMakerException extends RuntimeException{
    public NotFoundMakerException(String maker) {
        super("Product Id not found: " + maker);
    }
}
