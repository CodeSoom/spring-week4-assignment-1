package com.codesoom.assignment.controllers;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductErrorAdvice {

    /**
     * 404 상태코드와 함께 예외 내용을 응답합니다.
     *
     * @param e 예외 내용이 담긴 예외객체
     * @return 예외 내용
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return e.getMessage();
    }
}
