package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product 클래스는")
class ProductTest {

    @Nested
    @DisplayName("Product 객체 값이 존재 한다면")
    class Describe_Product {
        private final Product product = new Product();

        @Test
        @DisplayName("Id와 Title 값이 두 값 모두 반환한다.")
        void getProductWithResultofIdAndTitle() {
            product.setId(1L);
            product.setTitle("고양이 장난감");

            assertThat(product.getId()).isEqualTo(1L);
            assertThat(product.getTitle()).isEqualTo("고양이 장난감");
        }
    }
}
