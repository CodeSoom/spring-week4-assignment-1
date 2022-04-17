package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductReadService;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@ProductController
public class ProductReadController {

    private final ProductReadService service;

    public ProductReadController(ProductReadService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Product> getProducts() {
        return service.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Product getProductDetail(@PathVariable Long id) {
        return service.findById(id);
    }

}
