package com.codesoom.assignment.product.infra.api.web.v1;

import com.codesoom.assignment.common.dto.ErrorResponse;
import com.codesoom.assignment.product.application.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse productNotFoundExceptionHandler(ProductNotFoundException e) {
        return e.toErrorResponse();
    }

}
