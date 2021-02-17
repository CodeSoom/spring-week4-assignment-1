package com.codesoom.assignment.controllers;

import com.codesoom.assignment.models.Toy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductsController {
    @GetMapping
    public List<Toy> getAllProducts() {
        return null;
    }

    @GetMapping("{id}")
    public Toy getProduct(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Toy toy) {

    }

    @PatchMapping("{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Toy toy) {

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {

    }
}
