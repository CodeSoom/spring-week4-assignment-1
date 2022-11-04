package com.codesoom.assignment.products.application;

import com.codesoom.assignment.exception.products.ProductNotFoundException;
import com.codesoom.assignment.products.controllers.dto.request.ProductCreateRequest;
import com.codesoom.assignment.products.controllers.dto.request.ProductUpdateRequest;
import com.codesoom.assignment.products.domain.Product;
import com.codesoom.assignment.products.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Product createProduct(ProductCreateRequest productCreateRequest) {
        Product product = productCreateRequest.toEntity();

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        return null;
    }
}
