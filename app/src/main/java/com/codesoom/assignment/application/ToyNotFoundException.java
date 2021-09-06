package com.codesoom.assignment.application;

import java.util.NoSuchElementException;

public class ToyNotFoundException extends NoSuchElementException {
    Long id;

    public ToyNotFoundException(Long id) {
        this.id = id;
    }

    public String getId() {
        return id.toString();
    }
}
