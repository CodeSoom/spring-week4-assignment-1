package com.codesoom.assignment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found tasks")
public class TaskNotFoundException extends RuntimeException{
    public String TaskNotFoundException(Long id) {
        return id + "not found";

    }
}
