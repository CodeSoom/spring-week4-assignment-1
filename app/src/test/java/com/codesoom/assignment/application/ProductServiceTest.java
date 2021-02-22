package com.codesoom.assignment.application;

import com.codesoom.assignment.models.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository = mock(ProductRepository.class);


    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    void getProducts() {
        given(productRepository.findAll()).willReturn(List.of());

        assertThat(productService.getProducts()).isEmpty();
    }

    @Test
    void getProduct() {
    }

    @Test
    void createProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}