package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.infra.persistence.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductReader {

    private final ProductRepository productRepository;

    public ProductReader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }
}
