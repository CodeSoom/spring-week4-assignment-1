package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CatToyControllerErrorAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CatToyNotFoundException.class)
    public ErrorResponse handleNotFound() {
        return new ErrorResponse("CatToy not found");
    }
}
