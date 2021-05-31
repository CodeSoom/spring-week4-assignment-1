package com.codesoom.assignment.catToy.controllers;

import com.codesoom.assignment.catToy.ToyNotFoundException;
import com.codesoom.assignment.task.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CatToyErrorAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ToyNotFoundException.class)
    public ErrorResponse handleNotFound() {
        return new ErrorResponse("Cat\'s Toy not found");
    }
}
