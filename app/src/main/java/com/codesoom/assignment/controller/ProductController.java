package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductApplicationService;
import com.codesoom.assignment.domain.Product;
import org.checkerframework.dataflow.qual.Deterministic;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductApplicationService applicationService;

    public ProductController(ProductApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<ProductDTO> getAllProduct() {
        List<Product> allProductList = this.applicationService.getAllProducts();
        return allProductList.stream()
            .map(ProductDTO::from)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getSpecificProduct(@PathVariable Long id) throws ProductNotFoundException {
        Product product = this.applicationService.getProduct(id).orElseThrow(
                () -> new ProductNotFoundException(id)
        );

        return ProductDTO.from(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody ProductDTO product) {
        Product createdProduct = applicationService.createProduct(
            product.name,
            product.maker,
            Integer.toString(product.price),
            product.imageURL
        );
        return ProductDTO.from(createdProduct);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        applicationService.deleteProduct(id);
    }
}
