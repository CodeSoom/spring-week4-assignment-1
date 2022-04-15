package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    @GetMapping("{id}")
    public Optional<Product> detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PutMapping("{id}")
    public Product updatePut(@PathVariable Long id, @RequestBody Product source) {
        return productService.updateProduct(id, source);
    }

    @PatchMapping("{id}")
    public Product updatePatch(@PathVariable Long id, @RequestBody Product source) {
        return productService.updateProduct(id, source);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
