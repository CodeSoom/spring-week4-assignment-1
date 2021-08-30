package com.codesoom.assignment.controller;

import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.exception.CatToyInvalidPriceException;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class CatToyErrorAdvice {

    @ResponseBody
    @ExceptionHandler(CatToyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse catToyNotFoundExceptionHandler(RuntimeException err) {
        log.info(err.getMessage());
        return new ErrorResponse(err.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(CatToyInvalidPriceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse catToyInvalidPriceExceptionHandler(RuntimeException err) {
        log.info(err.getMessage());
        return new ErrorResponse(err.getMessage());
    }
}
