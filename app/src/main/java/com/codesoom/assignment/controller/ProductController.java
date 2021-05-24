package com.codesoom.assignment.controller;

import com.codesoom.assignment.error.exception.ProductNotFoundException;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping("{id}")
    public Product getProduct(@PathVariable Long id) {
        return Optional.ofNullable(productService.get(id))
                       .orElseThrow(ProductNotFoundException::new);
    }
}
