package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.ProductJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductJpaRepository productJpaRepository;

    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

}
