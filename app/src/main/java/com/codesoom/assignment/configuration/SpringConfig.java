package com.codesoom.assignment.configuration;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class SpringConfig {
    private final ProductRepository productRepository;

    @Autowired
    public SpringConfig(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Bean
    public ProductService productService() {
        return new ProductService(productRepository);
    }
}
