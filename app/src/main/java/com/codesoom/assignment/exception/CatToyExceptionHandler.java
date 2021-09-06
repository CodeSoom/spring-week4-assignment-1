package com.codesoom.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 장난감 관련 예외를 처리하는 핸들러입니다.
 */

@ControllerAdvice
public class CatToyExceptionHandler {

    /**
     * 장난감을 찾지 못한 경우, 예외를 받아 처리합니다.
     */
    @ResponseStatus(code=HttpStatus.NOT_FOUND, reason = "No Such Toy Found")
    @ExceptionHandler(CatToyNotFoundException.class)
    public void catToyNotFoundException() { }
}
