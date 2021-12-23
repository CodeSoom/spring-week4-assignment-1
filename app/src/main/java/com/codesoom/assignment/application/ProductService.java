package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public void createProduct(Product source) {
        Product product = new Product(source);
        productRepository.save(product);
    }


    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
