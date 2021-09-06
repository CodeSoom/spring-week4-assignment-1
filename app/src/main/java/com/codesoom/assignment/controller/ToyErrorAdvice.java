package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ToyNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ToyErrorAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ToyNotFoundException.class)
    public ErrorResponse handleNotFound(ToyNotFoundException e) {
        return new ErrorResponse("Toy " + e.getId() + " not found");
    }
}
