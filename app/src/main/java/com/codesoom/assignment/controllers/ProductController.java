// TODO
// 1. GET /products
// 2. GET /products/{id}
// 3. POST /products
// 4. PATCH /products/{id}
// 5. DELETE /products/{id}
package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("{id}")
    public Product findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(Product product) {
        return productService.save(product);
    }



}
