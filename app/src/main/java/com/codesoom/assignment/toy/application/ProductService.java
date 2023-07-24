package com.codesoom.assignment.toy.application;

import com.codesoom.assignment.toy.domain.Product;
import com.codesoom.assignment.toy.domain.dto.ProductRequest;
import com.codesoom.assignment.toy.domain.dto.ProductResponse;
import com.codesoom.assignment.toy.infra.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest product) {
        Product savedProduct = productRepository.save(product.toProductEntity());
        log.info("Product created: {}", savedProduct);
        return new ProductResponse(savedProduct);
    }
}
