package com.codesoom.assignment;

public class CatToyNotFoundException extends RuntimeException {

    public  CatToyNotFoundException(Long id) {
        super("Task not found:" + id);
    }
}
