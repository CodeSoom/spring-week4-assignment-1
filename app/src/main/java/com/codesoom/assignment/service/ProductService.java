package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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

    public ProductResponse getProduct(Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(()-> new ProductNotFoundException("저장되지 않은 상품 id가 주어졌습니다."));

        return new ProductResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, Product product) throws ProductNotFoundException {
        Product storedProduct = productRepository
                .findById(id)
                .orElseThrow(()-> new ProductNotFoundException("저장되지 않은 상품 id가 주어졌습니다."));

        Product updateProduct = product.update(storedProduct);

        return new ProductResponse(updateProduct);
    }

    @Transactional
    public void deleteProduct(Long id) throws ProductNotFoundException {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("저장되지 않은 상품 id가 주어졌습니다."));

        productRepository.delete(product);
    }
}
