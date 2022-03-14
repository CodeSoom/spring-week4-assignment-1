package com.codesoom.assignment.application;

public class CatToyNotFoundException extends RuntimeException {
    CatToyNotFoundException(Long id) {
        super("CatToy not found : " + id);
    }
}
