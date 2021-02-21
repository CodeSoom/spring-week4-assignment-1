package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductApplicationService;
import com.codesoom.assignment.domain.Product;
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
    public ProductDTO getSpecificProduct(@PathVariable Long id) {
        Optional<Product> product = this.applicationService.getProduct(id);
        return ProductDTO.from(product.get());
    }

    @PostMapping
    public ProductDTO createProduct(ProductDTO product) {
        Product createdProduct = applicationService.createProduct(product.name, product.maker, product.price, product.imageURL);
        return ProductDTO.from(createdProduct);
    }
}
