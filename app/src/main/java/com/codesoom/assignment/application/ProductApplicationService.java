package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductId;
import com.codesoom.assignment.domain.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductApplicationService {
    ProductRepository productRepository;

    public ProductApplicationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(String name, String maker, String price, String imageURL) {
        ProductId id = productRepository.nextId();
        Product newProduct = new Product(id, name, maker, price, imageURL);
        productRepository.save(newProduct);
        return newProduct;
    }

    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.find(id);
        productRepository.remove(product.get());
    }
}
