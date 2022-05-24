package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductCommandDto;
import com.codesoom.assignment.dto.ProductDto;
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

    public ProductDto updateProduct(Long id, ProductCommandDto productCommandDto) {
        Product product = productRepository.findById(id).get();
        product.update(
                productCommandDto.getName(),
                productCommandDto.getMaker(),
                productCommandDto.getPrice(),
                productCommandDto.getImageUrl());
        return ProductDto.of(product);
    }
}
