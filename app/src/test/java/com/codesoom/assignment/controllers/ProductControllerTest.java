package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.Test;

class ProductControllerTest {

    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController();
    }

    @Test
    void createController() {
        assertThat(productController).isNotNull();
    }
}
