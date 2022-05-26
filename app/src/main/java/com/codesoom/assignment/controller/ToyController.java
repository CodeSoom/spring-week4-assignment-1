package com.codesoom.assignment.controller;

import com.codesoom.assignment.interfaces.ProductController;
import com.codesoom.assignment.interfaces.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ToyController implements ProductController {
    private final ProductService productService;

    public ToyController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @GetMapping
    public List<Product> getProducts() {
        List<Product> toys = productService.findProducts();
        return toys;
    }

    @Override
    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable Long id) {
        // 고민중
        return Optional.ofNullable(productService.findProduct(id));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(Product product) {
        return productService.createProduct(product);
    }
}
