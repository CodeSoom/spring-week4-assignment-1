package com.codesoom.assignment.service;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    return new ProductNotFoundException();
                });
        return product;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> {
                    return new ProductNotFoundException();
                });
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product source) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    return new ProductNotFoundException();
                });
        product.setName(source.getName());
        product.setMaker(source.getMaker());
        product.setPrice(source.getPrice());
        product.setImageUrl(source.getImageUrl());
        return productRepository.save(product);
    }
}
