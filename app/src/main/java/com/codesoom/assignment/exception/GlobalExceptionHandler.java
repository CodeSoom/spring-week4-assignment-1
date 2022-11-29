package com.codesoom.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CommonException.class})
    protected ResponseEntity<ErrorResponse> handleCommonException(final CommonException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponse.from(exception));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponse> handleException(final HttpServletRequest request,
                                                            final Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.from(request, exception));
    }
}
