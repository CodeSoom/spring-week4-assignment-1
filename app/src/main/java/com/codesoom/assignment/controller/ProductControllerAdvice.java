package com.codesoom.assignment.controller;

import com.codesoom.assignment.dto.ErrorResult;
import com.codesoom.assignment.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice(basePackageClasses = ProductController.class)
public class ProductControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResult resourceNotFound(ResourceNotFoundException e){
        return new ErrorResult(e.getMessage());
    }
}
