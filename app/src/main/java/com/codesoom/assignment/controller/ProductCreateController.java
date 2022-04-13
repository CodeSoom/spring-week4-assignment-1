package com.codesoom.assignment.controller;


import com.codesoom.assignment.application.ProductCreateService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ProductController
public class ProductCreateController {

    private final ProductCreateService service;

    public ProductCreateController(ProductCreateService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product createProduct(@RequestBody ProductDto productDto) {
        return service.create(productDto);
    }

}
