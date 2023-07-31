package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.dto.ProductRequest;
import com.codesoom.assignment.product.infra.persistence.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductCreator {
    private final ProductRepository productRepository;

    public ProductCreator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public Product createProduct(ProductRequest product) {
        return productRepository.save(product.toProductEntity());
    }
}
