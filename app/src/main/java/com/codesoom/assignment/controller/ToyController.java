package com.codesoom.assignment.controller;

import com.codesoom.assignment.interfaces.ProductController;
import com.codesoom.assignment.interfaces.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ToyController implements ProductController {
    private final ProductService service;

    public ToyController(ProductService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public List<Product> getProducts() {
        return service.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return service.findById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return service.save(product);
    }

    @Override
    @RequestMapping(value = "/{id}",
            method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Product updateProduct(@PathVariable Long id, @RequestBody Product newProduct) {
        return service.update(id, newProduct);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        service.delete(id);
    }
}
