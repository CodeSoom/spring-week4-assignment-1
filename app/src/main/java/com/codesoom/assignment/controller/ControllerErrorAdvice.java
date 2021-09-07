package com.codesoom.assignment.controller;

import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.eception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerErrorAdvice {

    public static final String CONTACT_NUMBER = "\n 문의사항 : 02-4725-1234";

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleNotFound(ProductNotFoundException e) {
        return new ErrorResponse(e.getMessage() + CONTACT_NUMBER);
    }
}
