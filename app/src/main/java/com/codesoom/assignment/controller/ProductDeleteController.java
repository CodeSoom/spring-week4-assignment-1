package com.codesoom.assignment.controller;


import com.codesoom.assignment.application.ProductDeleteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@ProductController
public class ProductDeleteController {

    private final ProductDeleteService service;

    public ProductDeleteController(ProductDeleteService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}
