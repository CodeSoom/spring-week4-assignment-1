package com.codesoom.assignment.Toy.controllers;

import com.codesoom.assignment.Toy.ToyNotFoundException;
import com.codesoom.assignment.Toy.dto.ErrorResponse;
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
    public ErrorResponse handleNotFound() {
        return new ErrorResponse("Toy not found");
    }
}
