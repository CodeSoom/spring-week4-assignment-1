package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ProductControllerTest {

    private ProductController productController;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    @DisplayName("제품 목록 조회")
    void findAll() {
        assertThat(productController.findAll()).isEmpty();
    }

    @Test
    @DisplayName("특정 제품 목록 조회")
    void findOne() {
        Product product = productController.findOne(1L);
        assertThat(product).isNotNull();
    }


}