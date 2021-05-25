package com.codesoom.assignment;

public class ToyNotFoundException extends RuntimeException {
    public ToyNotFoundException(Long id) {
        super("Cat\'s Toy not found : ID = "+ id);
    }
}
