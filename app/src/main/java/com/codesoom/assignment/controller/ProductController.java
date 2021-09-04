package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
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

    @PostMapping
    public Product create(Product source) {
        return productService.createProduct(source);
    }

    public Product update(long id, Product source) {
        return productService.updateProduct(id, source);
    }

    public Product delete(long id) {
        return productService.deleteProduct(id);
    }
}
