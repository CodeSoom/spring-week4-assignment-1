package com.codesoom.assignment.errors;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long id) {
        super("ProductNotFoundException : " + id);
    }
}
