package com.codesoom.assignment.controller;

import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class NotFoundErrorAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ProductNotFoundException.class, ProductNotFoundException.class})
    public ErrorResponse handlerProductNotFound() {
        return new ErrorResponse("Product Not Found");
    }
}
