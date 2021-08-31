package com.codesoom.assignment.controller;

import java.util.ArrayList;
import java.util.List;

import com.codesoom.assignment.Dto.CreateProductDto;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> list() {
        return new ArrayList<>();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody final CreateProductDto createProductDto) {
        Product product = new Product(createProductDto.getTitle());
        return productService.createProduct(product);
    }
}
