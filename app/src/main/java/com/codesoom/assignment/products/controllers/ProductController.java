package com.codesoom.assignment.products.controllers;

import com.codesoom.assignment.products.application.ProductService;
import com.codesoom.assignment.products.domain.Product;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> list() {
        return productService.getProducts();
    }
}
