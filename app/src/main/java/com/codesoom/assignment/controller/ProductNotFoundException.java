package com.codesoom.assignment.controller;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(int id) {
        super("Product not found: " + id);
    }
}
