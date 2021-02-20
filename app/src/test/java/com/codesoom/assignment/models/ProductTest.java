package com.codesoom.assignment.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    void creationWithoutId() {
        Product product = new Product("티셔츠", "나이키", 40000);

        assertThat(product.getName()).isEqualTo("티셔츠");
        assertThat(product.getMaker()).isEqualTo("나이키");
        assertThat(product.getPrice()).isEqualTo(40000);
        assertThat(product.getImageUrl()).isNull();
    }

    @Test
    void creationWithId() {
        Product product = new Product  (1L, "티셔츠", "나이키", 40000);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("티셔츠");
        assertThat(product.getMaker()).isEqualTo("나이키");
        assertThat(product.getPrice()).isEqualTo(40000);
        assertThat(product.getImageUrl()).isNull();
    }


}