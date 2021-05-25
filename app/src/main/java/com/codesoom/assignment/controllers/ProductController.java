package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Product;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("")
    public void getProductList() {

    }

    @GetMapping("/{productId}")
    public void getDetailProduct(@PathVariable Long productId) {

    }

    @PostMapping("")
    public void createProduct(@RequestBody Product product) {

    }

    @PatchMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId) {

    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {

    }
}
