package com.codesoom.assignment.controller;

import com.codesoom.assignment.ProductNotFoundException;
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
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final CatToyService service;

    public ProductController(CatToyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> findById(@PathVariable(name = "id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody Product Product) {
        return service.save(Product);
    }

    @PatchMapping("/{id}")
    public Product updateById(@PathVariable(name = "id") Long id, @RequestBody Product toy) {
        if (service.findById(id).isEmpty()) {
            throw new ProductNotFoundException();
        }

        return service.save(toy);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Long id) {
        if (service.findById(id).isEmpty()) {
            throw new ProductNotFoundException();
        }

        service.deleteById(id);
    }
}
