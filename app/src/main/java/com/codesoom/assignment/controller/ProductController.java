package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @GetMapping("{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PatchMapping("{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product){
        return productService.update(id,product);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }

}
