package com.codesoom.assignment.products.controllers;

import com.codesoom.assignment.products.application.ProductService;
import com.codesoom.assignment.products.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }
}
