package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return new ProductResponse(savedProduct);
    }
}
