package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    private final ProductService service;

    public ProductControllerTest(ProductService service) {
        this.service = service;
    }

}