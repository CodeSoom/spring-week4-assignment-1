package com.codesoom.assignment.web;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.dto.ProductCommandRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductCommandRequest productCommandRequest) {
        return productService.createTask(productCommandRequest);
    }

    @PatchMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id,
                                         @RequestBody ProductCommandRequest productCommandRequest) {
        return productService.updateProduct(id, productCommandRequest);
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
