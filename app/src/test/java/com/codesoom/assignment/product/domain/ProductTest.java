package com.codesoom.assignment.product.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스")
public class ProductTest {
    private static final String NAME = "장난감뱀";
    private static final String MAKER = "애옹이네 장난감";
    private static final int PRICE = 5000;
    private static final String IMAGE_URL = "https://http.cat/599";

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .imageUrl(IMAGE_URL)
                .build();
    }

    @DisplayName("getName은 상품명을 리턴한다")
    @Test
    void getName() {
        assertThat(product.getName()).isEqualTo(NAME);
    }

    @DisplayName("getMaker은 메이커를 리턴한다")
    @Test
    void getMaker() {
        assertThat(product.getMaker()).isEqualTo(MAKER);
    }

    @DisplayName("getPrice 상품 가격을 리턴한다")
    @Test
    void getPrice() {
        assertThat(product.getPrice()).isEqualTo(PRICE);
    }

    @DisplayName("getImageUrl은 상품명을 리턴한다")
    @Test
    void getImageUrl() {
        assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
    }
}
