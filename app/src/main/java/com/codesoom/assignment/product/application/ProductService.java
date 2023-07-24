package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.dto.ProductRequest;
import com.codesoom.assignment.product.domain.dto.ProductResponse;
import com.codesoom.assignment.product.infra.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ProductResponse> getProductList() {
        return ProductResponse.listOf(productRepository.findAll());
    }

    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return new ProductResponse(product);
    }
}
