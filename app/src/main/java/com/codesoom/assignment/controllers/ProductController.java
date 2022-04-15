package com.codesoom.assignment.controllers;


import com.codesoom.assignment.domains.Product;
import com.codesoom.assignment.domains.ProductReqDto;
import com.codesoom.assignment.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("")
    public List<Product> list() {
        return productService.getProducts();
    }

    @PostMapping("")
    public Product add(@RequestBody ProductReqDto newProduct) {
        return productService.create(newProduct);
    }

}
