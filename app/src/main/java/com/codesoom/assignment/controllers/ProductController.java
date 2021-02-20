package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @GetMapping
    List<Product> list() {
        Product product = new Product(1L, "오뎅꼬치", "야옹아멍멍해봐", 3000);

        return List.of(product);
    }
}
