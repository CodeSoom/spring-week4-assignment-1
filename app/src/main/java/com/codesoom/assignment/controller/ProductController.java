package com.codesoom.assignment.controller;

import com.codesoom.assignment.service.ProductService;
import org.springframework.web.bind.annotation.RestController;

/**
 * controls any mapping to /products
 */

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
