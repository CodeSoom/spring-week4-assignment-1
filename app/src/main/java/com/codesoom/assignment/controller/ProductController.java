package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> list(){
        return productService.getProducts();
    }

    @GetMapping("{id}")
    public Product findById(@PathVariable long id) {
        return productService.getProduct(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product create(Product source) {
        return productService.createProduct(source);
    }

    @PatchMapping("{id}")
    public Product update(@PathVariable long id, Product source) {
        return productService.updateProduct(id, source);
    }

    @DeleteMapping("{id}")
    public Product delete(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}
