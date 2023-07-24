package com.codesoom.assignment.toy.infra.api;

import com.codesoom.assignment.toy.application.ProductService;
import com.codesoom.assignment.toy.domain.Product;
import com.codesoom.assignment.toy.domain.dto.ProductRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public Product createProduct(ProductRequest productRequest) {
        return null;
    }

}
