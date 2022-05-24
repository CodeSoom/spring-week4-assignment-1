package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto createTask(ProductDto productDto) {
        Product product = new Product(
                productDto.getName(),
                productDto.getMaker(),
                productDto.getPrice(),
                productDto.getImageUrl());
        Product newProduct = productRepository.save(product);
        return ProductDto.of(newProduct);
    }
}
