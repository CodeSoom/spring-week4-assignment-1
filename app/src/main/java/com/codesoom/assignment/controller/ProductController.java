package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductServiceImpl;
import com.codesoom.assignment.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl service;

    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> findById(@RequestParam Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody Product Product) {
        return service.save(Product);
    }

    @PatchMapping("/{id}")
    public Product updateById(@RequestParam Long id, @RequestBody Product toy) {
//        return service.save(id, toy);
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestParam Long id) {
        service.deleteById(id);
    }
}
