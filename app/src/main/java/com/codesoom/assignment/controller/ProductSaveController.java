package com.codesoom.assignment.controller;


import com.codesoom.assignment.application.ProductSaveService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ProductController
public class ProductSaveController {

    private final ProductSaveService service;

    public ProductSaveController(ProductSaveService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product saveProduct(@RequestBody ProductDto productDto) {
        return service.saveProduct(productDto);
    }

}
