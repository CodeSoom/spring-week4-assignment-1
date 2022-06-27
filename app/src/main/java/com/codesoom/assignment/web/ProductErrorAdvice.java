package com.codesoom.assignment.web;

import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductErrorAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ErrorResponse productNotFoundException(ProductNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
