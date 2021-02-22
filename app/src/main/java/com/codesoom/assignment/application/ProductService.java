package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.models.Product;
import com.codesoom.assignment.models.ProductRepository;
import com.codesoom.assignment.models.Task;

import java.awt.print.PrinterException;
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
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return product;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct( Long id, Product source) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.change(source);
        
        return product;
    }

    public Product deleteProduct(long id) {
        return null;
    }
}
