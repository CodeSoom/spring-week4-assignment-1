package com.codesoom.assignment.controller;

import com.codesoom.assignment.interfaces.ProductController;
import com.codesoom.assignment.interfaces.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ToyController implements ProductController {
    private final ProductService productService;

    public ToyController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @GetMapping
    public List<Product> getProducts() {
        List<Product> toys = productService.findProducts();
        return toys;
    }

    @Override
    @PostMapping
    public Optional<Product> getProduct(Long id) {
        return Optional.empty();
    }
}
