package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exception.ProductNotFoundException;
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
        return findProductById(id);
    }

    public Product saveProduct(Product source) {
        return productRepository.save(source);
    }

    public Product updateProduct(Long id, Product source) {
        Product product = findProductById(id);
        return  productRepository.save(source);
    }

    public Product deleteProduct(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
        return product;
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
