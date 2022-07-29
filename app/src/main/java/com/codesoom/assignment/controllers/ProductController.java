package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Collection;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public Collection<Product> list(){
        return productService.getProducts();
    }

    @GetMapping("{id}")
    public Product detail(@PathVariable Long id){
        return productService.getToys(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product source){
        return productService.createToy(source);
    }

    @PutMapping("{id}")
    public Product update(@PathVariable Long id, @RequestBody Product source){
        return productService.updateToy(id, source);
    }

    @PatchMapping("{id}")
    public Product patch(@PathVariable Long id, @RequestBody Product source){
        return productService.updateToy(id, source);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        productService.deleteToy(id);
    }
}
