package com.codesoom.assignment.controllers;

import com.codesoom.assignment.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController{
    @GetMapping
    public List<Product> list() {
        Product product = new Product(1L, "티셔츠", "나이키", 40000);
        return List.of(product);
    }
}
