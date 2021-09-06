package com.codesoom.assignment.controller;

import javax.servlet.http.HttpServletRequest;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ErrorResponseDto;

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
     * @see ProductService#detailProduct(Long)
     *
     * @see ProductController#update(Long)
     * @see ProductService#updateProduct(Long, Product)
     *
     * @see ProductController#delete(Long)
     * @see ProductService#deleteProduct(Long)
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponseDto handleNotFound(final HttpServletRequest request) {
            final String url = request.getRequestURI();
            final String method = request.getMethod();
            final String error = "Product를 찾을 수 없습니다." + " id를 확인해 주세요.";
        return new ErrorResponseDto(url, method, error);
    }
}
