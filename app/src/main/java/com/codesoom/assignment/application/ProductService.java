package com.codesoom.assignment.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProducts() {
        return null;
    }

    public Product getProduct(Long id) {
        return null;
    }

    public Product updateProduct(Product product) {
        return null;
    }

    public void deleteProduct(Product product) {
    }
}
