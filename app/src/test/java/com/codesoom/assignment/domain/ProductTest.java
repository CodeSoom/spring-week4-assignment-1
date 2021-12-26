package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스")
class ProductTest {

    private Product product0;
    private Product product1;

    @BeforeEach
    void setUp() {
        List<Product> products = ProductTestCase.getTestProducts(2);
        product0 = products.get(0);
        product1 = products.get(1);
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @BeforeEach
        void setUP() {
            product1.update(product0);
        }

        @Test
        @DisplayName("요청받은 product로 수정된 product를 리턴한다.")
        void it_returns_product_that_update_by_request_product() {
            assertThat(product0.getName()).isEqualTo(product1.getName());
            assertThat(product0.getMaker()).isEqualTo(product1.getMaker());
            assertThat(product0.getPrice()).isEqualTo(product1.getPrice());
            assertThat(product0.getImageUrl()).isEqualTo(product1.getImageUrl());
        }
    }
}
