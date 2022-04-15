package com.codesoom.assignment.controllers;


import com.codesoom.assignment.domains.Product;
import com.codesoom.assignment.dto.ProductReqDto;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import com.codesoom.assignment.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Product add(@RequestBody ProductReqDto newProduct) {
        newProduct.validation();
        return productService.create(newProduct);
    }

    @GetMapping("/{id}")
    public Product detail(@PathVariable("id") Long productId) {
        validationForProductId(productId);

        return productService.getProduct(productId);
    }

    private void validationForProductId(Long productId) {
        if (productId <= 0) {
            throw new ProductNotFoundException();
        }
    }


}
