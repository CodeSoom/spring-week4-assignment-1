package com.codesoom.assignment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void creationWithoutId() {
        Product product = new Product("쥐돌이", "냥이월드", 5000);
        assertThat(product.getName()).isEqualTo("쥐돌이");
        assertThat(product.getMaker()).isEqualTo("냥이월드");
        assertThat(product.getPrice()).isEqualTo(5000);
        assertThat(product.getImageUrl()).isEqualTo(null);
    }

    @Test
    void creationWithId() {
        Product product = new Product(1L,"쥐돌이", "냥이월드", 5000);
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

}
