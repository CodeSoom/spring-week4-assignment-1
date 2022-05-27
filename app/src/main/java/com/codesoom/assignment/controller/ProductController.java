package com.codesoom.assignment.controller;

import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductDTO.Response> getProducts() {
        return productService.getProducts();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO.Response> getProduct(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO.Response> createProduct(@RequestBody ProductDTO.CreateProduct createProduct) {
        ProductDTO.Response response = productService.createProduct(createProduct);
        URI uri = URI.create("http://localhost:8080/products/" + response.getId());
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO.Response> updateProduct(@PathVariable("id") int id,
                                                             @RequestBody ProductDTO.UpdateProduct updateProduct) {
        return ResponseEntity.ok(productService.updateProduct(id, updateProduct));
    }
}
