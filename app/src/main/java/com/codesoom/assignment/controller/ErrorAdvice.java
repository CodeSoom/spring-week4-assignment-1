package com.codesoom.assignment.controller;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.handler.ErrorHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice("com.codesoom.assignment")
public class ErrorAdvice {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorHandler TNFEHandler(ProductNotFoundException te) {
        return new ErrorHandler("상품이 존재하지 않습니다. 다시 한 번 확인해주세요.");
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NullPointerException.class)
    public ErrorHandler NPEHandler(NullPointerException ne) {
        return new ErrorHandler("입력 값이 존재하지 않습니다. 다시 한 번 확인해주세요.");
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorHandler notReadableExHandler(HttpMessageNotReadableException he) {
        return new ErrorHandler("상품의 [이름, 메이커, 가격, 이미지 주소]가 필요합니다. 다시 한 번 확인해주세요.");
    }

    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorHandler methodNotSupportedExHandler(HttpRequestMethodNotSupportedException he) {
        return new ErrorHandler("Id를 입력해주세요.");
    }
}
