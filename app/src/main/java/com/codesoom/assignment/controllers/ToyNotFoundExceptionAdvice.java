package com.codesoom.assignment.controllers;

import com.codesoom.assignment.exceptions.ToyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ToyNotFoundExceptionAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ToyNotFoundException.class)
    public ToyNotFoundException handleNotFound(Long id) {
        return new ToyNotFoundException(id);
    }
}

