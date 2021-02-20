package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    void creation() {
        Product product = new Product(1L, "오뎅꼬치", "야옹아멍멍해봐", 3000);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("오뎅꼬치");
        assertThat(product.getMaker()).isEqualTo("야옹아멍멍해봐");
        assertThat(product.getPrice()).isEqualTo(3000);
    }
}