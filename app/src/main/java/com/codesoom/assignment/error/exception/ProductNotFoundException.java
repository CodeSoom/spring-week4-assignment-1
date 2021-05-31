package com.codesoom.assignment.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ProductNotFoundException extends HttpClientErrorException {
    public ProductNotFoundException() {
        this("Product Not Found");
    }
    public ProductNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
