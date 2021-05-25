package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @GetMapping("")
    List<Product> list() {
        return productService.getAllProducts();
    }
}
