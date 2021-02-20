package com.codesoom.assignment.exceptions;

public class ToyNotFoundException extends RuntimeException {
    public ToyNotFoundException(Long id) {
        super("Not found toy id: " + id);
    }
}
