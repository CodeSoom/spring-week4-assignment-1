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

    public CatToyNotFoundException(Long id) {
        super("id " + id + "를 가지는 CatToy가 없습니다.");
    }

    public CatToyNotFoundException(Throwable cause) {
        super(cause);
    }
}
