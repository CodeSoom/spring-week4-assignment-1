package com.codesoom.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *  컨트롤러 메서드와 관계된 예외를 처리하는 핸들러입니다.
 */
@ControllerAdvice
public class ControllerMethodExceptionHandler {

    /**
     * 컨트롤러 메소드 Argument 타입이 일치하지 않는 경우의 예외를 처리합니다.
     *
     * @param e MethodArgumentTypeMismatchException 예외
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Method Argument Type Mismatch Exception")
    public void handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    }
}