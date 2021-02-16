package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    Product product;
    final Long ID = 1L;
    final String NAME = "장난감 뱀";
    final String MAKER = "애옹이네 장난감";
    final Integer PRICE = 5000;
    final String IMG = "orgin.jpg";

    @BeforeEach
    void setUp() {
        product = new Product(ID, NAME, MAKER, PRICE, IMG);
    }

    @Test
    @DisplayName("getId 테스트")
    void getId() {
        assertThat(product.getId()).isEqualTo(ID);
    }

    @Test
    @DisplayName("getName 테스트")
    void getName() {
        assertThat(product.getName()).isEqualTo(NAME);
    }

    @Test
    @DisplayName("getMaker() 테스트")
    void getMaker() {
        assertThat(product.getMaker()).isEqualTo(MAKER);
    }

    @Test
    @DisplayName("getPrice() 테스트")
    void getPrice() {
        assertThat(product.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("getImg() 테스트")
    void getImg() {
        assertThat(product.getImg()).isEqualTo(IMG);
    }
}
