package com.codesoom.assignment.application;

import javax.transaction.Transactional;

import com.codesoom.assignment.Dto.CreateProductDto;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.Product;

import org.springframework.stereotype.Service;

@Service
@Transactional
public final class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    Product createProduct(final CreateProductDto createProductDto) {
        Product product = new Product(createProductDto.getTitle());
        return productRepository.save(product);
    }
}
