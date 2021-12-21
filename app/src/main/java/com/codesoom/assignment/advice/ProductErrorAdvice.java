package com.codesoom.assignment.advice;

import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.exception.ProductNotFoundException;
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
    public ErrorResponse handleNotFound(ProductNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
