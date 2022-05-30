package com.codesoom.assignment.controller;

import com.codesoom.assignment.exception.ToyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandler {

    private final String TOY_NOT_FOUND = "장난감을 찾을 수 없습니다. id를 확인해주세요.";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ToyNotFoundException.class)
    public String handleNotFoundException() {
        return TOY_NOT_FOUND;
    }
}