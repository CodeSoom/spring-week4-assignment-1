package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.dto.ProductCommandDto;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto createTask(ProductCommandDto productCommandDto) {
        Product product = productCommandDto.toEntity();
        Product newProduct = productRepository.save(product);
        return ProductDto.of(newProduct);
    }
}
