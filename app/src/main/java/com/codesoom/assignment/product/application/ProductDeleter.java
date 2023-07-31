package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.infra.persistence.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductDeleter {

    private final ProductRepository productRepository;
    private final ProductReader productReader;

    public ProductDeleter(ProductRepository productRepository, ProductReader productReader) {
        this.productRepository = productRepository;
        this.productReader = productReader;
    }

    public void deleteProduct(Long id) {
        Product product = productReader.getProduct(id);
        productRepository.delete(product);
    }
}
