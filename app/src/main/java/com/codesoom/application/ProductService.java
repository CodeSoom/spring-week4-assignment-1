package com.codesoom.application;

import com.codesoom.domain.Product;
import com.codesoom.domain.ProductRepository;
import com.codesoom.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.find(id);
    }

    public Product createProduct(Product product) {

        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Product source, Long id) {
        Optional<Product> product = productRepository.find(id);

        product.get().setName(source.getName());
        product.get().setPrice(source.getPrice());
        product.get().setMaker(source.getMaker());
        product.get().setImageUrl(source.getImageUrl());

        return product;
    }

    public List<Product> deleteProduct(Long id) {
        Optional<Product> product = productRepository.find(id);

        productRepository.remove(product);
        return getProducts();
    }
}
