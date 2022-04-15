package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductUpdateService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@ProductController
public class ProductUpdateController {

    private final ProductUpdateService service;

    public ProductUpdateController(ProductUpdateService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public Product update(@PathVariable Long id,
                          @RequestBody ProductDto productDto) {
        return service.update(id, productDto);
    }

}
