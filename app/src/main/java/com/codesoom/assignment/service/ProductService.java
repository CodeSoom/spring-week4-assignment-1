package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductJpaRepository productJpaRepository;

    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    public void deleteAll() {
        productJpaRepository.deleteAll();
    }

}
