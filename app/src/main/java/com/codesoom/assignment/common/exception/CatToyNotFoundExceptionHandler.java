package com.codesoom.assignment.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CatToyNotFoundExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatToyNotFoundExceptionHandler.class);

    @ExceptionHandler({CatToyNotFoundException.class, IllegalArgumentException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void CatToyNotFoundException(Exception e) {
        LOGGER.error("error log = {}", e.toString());
    }
}
