package com.codesoom.assignment.dto;

public class CatToyNotFoundException extends RuntimeException{
    public CatToyNotFoundException() {
        super();
    }

    public CatToyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CatToyNotFoundException(String message) {
        super(message);
    }

    public CatToyNotFoundException(Throwable cause) {
        super(cause);
    }
}
