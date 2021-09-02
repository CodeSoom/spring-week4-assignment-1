package com.codesoom.assignment.product.controller;

import com.codesoom.assignment.common.dto.ErrorResponse;
import com.codesoom.assignment.product.exception.ProductInvalidPriceException;
import com.codesoom.assignment.product.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 고양이 장난감 Http Request 요청 처리 중 예외가 발생할 경우
 * 예외를 처리하고 반환한다.
 */
@Slf4j
@ControllerAdvice
public class ProductErrorAdvice {

    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse catToyNotFoundExceptionHandler(RuntimeException err) {
        log.info(err.getMessage());
        return new ErrorResponse(err.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ProductInvalidPriceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse catToyInvalidPriceExceptionHandler(RuntimeException err) {
        log.info(err.getMessage());
        return new ErrorResponse(err.getMessage());
    }
}
