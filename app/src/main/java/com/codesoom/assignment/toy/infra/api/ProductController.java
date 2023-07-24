package com.codesoom.assignment.toy.infra.api;

import com.codesoom.assignment.toy.application.ProductService;
import com.codesoom.assignment.toy.domain.Product;
import com.codesoom.assignment.toy.domain.dto.ProductRequest;
import com.codesoom.assignment.toy.domain.dto.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        log.info("Product request: {}", productRequest);
        return productService.createProduct(productRequest);
    }

}
