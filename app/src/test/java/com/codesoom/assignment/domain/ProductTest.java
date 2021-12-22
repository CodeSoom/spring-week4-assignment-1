package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductTest")
class ProductTest {

    @Test
    @DisplayName("Product 객체에 Id와 Title 값이 주어지면 두 값 모두 반환한다.")
    void getProductWithResultofIdAndTitle() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("고양이 장난감");

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getTitle()).isEqualTo("고양이 장난감");
    }
}
