package com.codesoom.assignment.services;

import com.codesoom.assignment.domains.Product;
import com.codesoom.assignment.dto.ProductReqDto;
import com.codesoom.assignment.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    protected final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product create(ProductReqDto newProductInput) {
        return productRepository.save(newProductInput.toProduct());
    }
}
