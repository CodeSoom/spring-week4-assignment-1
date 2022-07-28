package com.codesoom.assignment.application;

public class ToyNotFoundException extends RuntimeException {
    public ToyNotFoundException(Long toyId) {
        super("Toy not found " + toyId);
    }
}
