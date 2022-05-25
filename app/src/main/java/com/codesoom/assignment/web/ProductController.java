package com.codesoom.assignment.web;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.dto.ProductCommandRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/products")
@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductCommandRequest productCommandRequest) {
        return productService.createTask(productCommandRequest);
    }
}
