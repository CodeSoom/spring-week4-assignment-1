package com.codesoom.assignment.application;

import com.codesoom.assignment.models.Product;
import com.codesoom.assignment.models.ProductRepository;
import com.codesoom.assignment.models.Task;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return null;
    }

    public Product createProduct(Product product) {
        return null;
    }

    public Product updateProduct( Long id, Product source) {
        return null;
    }

    public Product deleteProduct(long id) {
        return null;
    }
}
