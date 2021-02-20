package com.codesoom.assignment.controllers;

import java.util.List;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;

public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getProducts() {
        return productService.getProducts();
    }

    public Product getProduct(Long id) {
        return productService.getProduct(id);
    }

    public Product createProduct(Product product) {
        return productService.createProduct(product);
    }
}
