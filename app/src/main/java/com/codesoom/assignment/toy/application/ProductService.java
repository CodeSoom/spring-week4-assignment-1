package com.codesoom.assignment.toy.application;

import com.codesoom.assignment.toy.infra.persistence.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
