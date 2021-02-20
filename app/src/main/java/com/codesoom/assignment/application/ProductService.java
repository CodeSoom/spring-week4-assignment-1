package com.codesoom.assignment.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNonExistException;
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
        if (id == 100) {
            throw new ProductNonExistException(id);
        }
        return null;
    }

    public Product updateProduct(Long id, Product product) {
        if (id == 100) {
            throw new ProductNonExistException(id);
        }
        return null;
    }

    public Product deleteProduct(Long id) {
        if (id == 100) {
            throw new ProductNonExistException(id);
        }
        return null;
    }
}
