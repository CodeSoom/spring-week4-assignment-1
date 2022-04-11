package com.codesoom.assignment;

public class CatToyNotFoundException extends RuntimeException {
    public CatToyNotFoundException(Long id) {
        super("CatToy not found: " + id);
    }
}
