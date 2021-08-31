package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.CatToyService;
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
public class CatToyController {
    CatToyService catToyService;

    public CatToyController(CatToyService catToyService) {
        this.catToyService = catToyService;
    }

    @GetMapping
    public List<Product> list() {
        return catToyService.getProducts();
    }

    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return catToyService.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return catToyService.saveProduct(product);
    }

    @PatchMapping("{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return catToyService.updateProduct(id, product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        catToyService.deleteProduct(id);
    }
}
