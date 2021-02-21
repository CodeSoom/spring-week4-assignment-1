package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductApplicationService;
import com.codesoom.assignment.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public ProductDTO getSpecificProduct(@PathVariable Long id) {
        Optional<Product> product = this.applicationService.getProduct(id);
        return ProductDTO.from(product.get());
    }
}
