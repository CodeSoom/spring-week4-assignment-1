package com.codesoom.assignment;

public class ToyNotFoundException extends RuntimeException {
    public ToyNotFoundException(Long id) {
        super("Toy not found: " + id);
    }
}
