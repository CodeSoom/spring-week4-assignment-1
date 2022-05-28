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
    public List<Product> list() {
        return service.findProducts();
    }

    @Override
    @GetMapping("/{id}")
    public Product detail(@PathVariable Long id) {
        return service.findProduct(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(Product product) {
        return service.createProduct(product);
    }

    @Override
    @PatchMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}
