package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
            .name("장난감 뱀")
            .maker("애옹이네 장난감")
            .price(10000L)
            .imageUrl("http://localhost:8080/snake")
            .build();
    }

    @Test
    @DisplayName("상품 정보를 업데이트 한다.")
    void updateInfo() {
        Product source = Product.builder()
            .name("name")
            .maker("maker")
            .price(1000L)
            .imageUrl("...")
            .build();

        Product updatedProduct = product.updateInfo(source);

        assertThat(source).isEqualTo(updatedProduct);
    }
}

