package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 상품 관련 예외처리를 담당합니다.
 */
@ControllerAdvice
public class ProductErrorAdvice {

    /**
     * 상품을 찾지 못 했을때 에러 메세지를 리턴합니다.
     * @return 에러 메세지
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleNotFound() {
        return new ErrorResponse("Product Not Found");
    }
}
