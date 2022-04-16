package com.codesoom.assignment.advice;

import com.codesoom.assignment.controller.ProductController;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 상품에 대한 HTTP 요청 중 예외 대신 응답
 */
@RestControllerAdvice(basePackageClasses = ProductController.class)
public class ProductControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public void productNotFoundException() {
    }
}
