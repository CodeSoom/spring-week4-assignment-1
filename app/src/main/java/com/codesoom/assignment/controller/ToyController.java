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
    private final ProductService productService;

    public ToyController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @GetMapping
    public List<Product> list() {
        return productService.findProducts();
    }

    @Override
    @GetMapping("/{id}")
    public Product detail(@PathVariable Long id) {
        return productService.findProduct(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(Product product) {
        return productService.createProduct(product);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
