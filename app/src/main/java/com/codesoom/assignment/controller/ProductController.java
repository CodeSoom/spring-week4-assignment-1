package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> findAllProduct(){
        return service.findAll();
    }

    @GetMapping("{id}")
    public Product findProduct(@PathVariable Long id){
        Optional<Product> find = service.findById(id);
        return null;
    }

    @PostMapping
    public Product createProduct(Product product){
        return service.save(product);
    }

    @PatchMapping("{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody Product product){
        return service.update(id , product);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Long id){
        service.deleteById(id);
    }
}
