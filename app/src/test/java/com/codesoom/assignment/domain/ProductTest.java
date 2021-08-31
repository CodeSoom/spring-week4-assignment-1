package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        String name = "장난감 뱀";
        String maker = "애옹이네 장난감";
        long price = 10000L;
        String imageUrl = "http://localhost:8080/snake";

        product = new Product(name, maker, price, imageUrl);
    }

    @Test
    @DisplayName("상품 정보를 업데이트 한다.")
    void updateInfo() {
        Product source = new Product("name", "maker", 1000L, "...");

        Product updatedProduct = product.updateInfo(source);

        assertThat(source).isEqualTo(updatedProduct);
    }
}

