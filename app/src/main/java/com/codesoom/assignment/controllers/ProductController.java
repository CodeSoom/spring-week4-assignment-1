package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.CommandProductService;
import com.codesoom.assignment.application.QueryProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {
    private final CommandProductService commandProductService;
    private final QueryProductService queryProductService;

    public ProductController(CommandProductService commandProductService, QueryProductService queryProductService) {
        this.commandProductService = commandProductService;
        this.queryProductService = queryProductService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return queryProductService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return queryProductService.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return commandProductService.createProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return commandProductService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        commandProductService.deleteProduct(id);
    }
}
