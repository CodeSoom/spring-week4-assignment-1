package com.codesoom.assignment;

public class ToyNotFoundException extends RuntimeException {
    public ToyNotFoundException(Long toyId) {
        super("Toy not found " + toyId);
    }
}
