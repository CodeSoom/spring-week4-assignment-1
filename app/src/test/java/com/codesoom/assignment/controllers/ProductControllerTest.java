package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.infra.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductControllerTest {
    private ProductController controller;
    private InMemoryProductRepository taskRepository;

    @BeforeEach
    void setUp() {
        ProductService productService = new ProductService(taskRepository);
        controller = new ProductController(productService);
    }

    @Test
    void list() {
        assertThat(controller.list()).isEmpty();
    }

    @Test
    void createNewTask() {
        Product product = new Product();
        product.setName("Test");

        controller.create(product);

        assertThat(controller.list()).hasSize(1);
        assertThat(controller.list().get(0).getId()).isEqualTo(1L);
        assertThat(controller.list().get(0).getName()).isEqualTo("Test");
    }
}
