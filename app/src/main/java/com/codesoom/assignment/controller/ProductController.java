package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    Product product = new Product();

    public ProductController(Product product) {
        this.product = product;
    }

}
