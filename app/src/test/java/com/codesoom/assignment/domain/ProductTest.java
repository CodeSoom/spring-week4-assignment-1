package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스")
class ProductTest {
    @Test
    @DisplayName("Product 객체를 생성합니다.")
    void createproduct() {
        Product product = Product.builder()
                .name("제품")
                .maker("메이커")
                .price(1000)
                .image("http://test.com/test.jpg")
                .build();

        assertThat(product).isNotNull();
    }
}
