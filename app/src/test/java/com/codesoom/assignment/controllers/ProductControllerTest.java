package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.infra.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductControllerTest {

    ProductService productService;
    ProductRepository catToyRepository;

    @BeforeEach
    void setup(){
        catToyRepository = new InMemoryProductRepository();
        productService = new ProductService(catToyRepository);
    }

    @Test
    void list(){
        ProductController controller = new ProductController(productService);
        assertThat(controller.list()).isEmpty();
    }

}