package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product.ProductBuilder()
                            .id(1L)
                            .name("이름")
                            .maker("메이커")
                            .price(BigDecimal.valueOf(1000))
                            .imageUrl("imgURL")
                            .build();
    }

    @Test
    @DisplayName("getId는 상품의 아이디를 반환한다.")
    void getId() {
        assertThat(product.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getName는 상품의 이름을 반환한다.")
    void getName() {
        assertThat(product.getName()).isEqualTo("이름");
    }

    @Test
    @DisplayName("getMaker는 상품의 제작자를 반환한다.")
    void getMaker() {
        assertThat(product.getMaker()).isEqualTo("메이커");
    }

    @Test
    @DisplayName("getPrice는 상품의 가격을 반환한다.")
    void getPrice() {
        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(1000));
    }

    @Test
    @DisplayName("getImageUrl는 상품의 이미지 주소를 반환한다.")
    void getImageUrl() {
        assertThat(product.getImageUrl()).isEqualTo("imgURL");
    }

}