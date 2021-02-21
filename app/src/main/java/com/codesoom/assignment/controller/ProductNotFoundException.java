package com.codesoom.assignment.controller;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
