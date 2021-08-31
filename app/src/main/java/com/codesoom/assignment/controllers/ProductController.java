package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping()
    public Iterable<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public Product get(@PathVariable long id) {
        return productService.getProduct(id);
    }

    @PatchMapping("{id}")
    public Product update(@PathVariable long id, @RequestBody Product source) {
        return productService.updateProduct(id, source);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Product> handleException() {
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
