package com.codesoom.assignment.controller;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.Dto.ErrorResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Product 리소스 예외처리를 담당한다.
 */
@ControllerAdvice
public final class ProductErrorAdvise {
    /**
     * ProductNotFoundException을 처리한다.
     * 
     * @return 에러 메시지("Product not found.")
     * 
     * @see ProductController#detail(Long)
     * @see com.codesoom.assignment.application.ProductService#detailProduct(Long)
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponseDto handleNotFound() {
        return new ErrorResponseDto("Product not found.");
    }
}
