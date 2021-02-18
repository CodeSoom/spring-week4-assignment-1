package com.codesoom.assignment.controller;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductControllerTest {
    @Test
    void getAllProduct() {
        ProductController controller = new ProductController();

        List<ProductDTO> products = controller.getAllProduct();

        assertThat(products).hasSize(0);
    }
}
