package com.codesoom.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ToyNotFoundException extends RuntimeException {
    public ToyNotFoundException(Long id) {
        super("Not found toy id: " + id);
    }
}
