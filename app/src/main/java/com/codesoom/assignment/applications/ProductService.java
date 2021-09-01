package com.codesoom.assignment.applications;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    Product save(Product product) {
        return productRepository.save(product);
    }

    List<Product> findAll() {
        return productRepository.findAll();
    }

    Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException(id));
    }

    void update(Long id, Product product) {
        productRepository.update(id, product);
    }

    void deleteById(Long id) {
        productRepository.deleteById(id);
    }


}
