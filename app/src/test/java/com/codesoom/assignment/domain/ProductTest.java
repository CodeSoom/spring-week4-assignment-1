package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    void creation() {
        Product product = new Product();

        assertThat(product.getName()).isEmpty();
    }
}