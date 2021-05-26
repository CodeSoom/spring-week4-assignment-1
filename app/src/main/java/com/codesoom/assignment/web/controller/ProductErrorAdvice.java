package com.codesoom.assignment.web.controller;

import com.codesoom.assignment.web.exception.InvalidProductException;
import com.codesoom.assignment.web.exception.ProductNotFoundException;
import com.codesoom.assignment.web.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductErrorAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleNotFound() {
        return new ErrorResponse("Product not found");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidProductException.class)
    public ErrorResponse handleBadRequest() {
        return new ErrorResponse("Invalid product");
    }
}
