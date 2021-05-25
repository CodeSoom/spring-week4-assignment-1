package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스")
class ProductTest {
    private static final String PRODUCT_NAME = "고양이가게";
    private static final String PRODUCT_MAKER = "장난감가게 메이커";
    private static final int PRODUCT_PRICE = 5000;
    private static final String PRODUCT_IMAGE_URL = "https://http.cat/599";

    private static final String UPDATE_POSTFIX = "_update";

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .price(PRODUCT_PRICE)
                .imgUrl(PRODUCT_IMAGE_URL)
                .build();
    }

    @DisplayName("getName은 상품명을 리턴한다")
    @Test
    void getName() {
        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
    }

    @DisplayName("getMaker은 메이커를 리턴한다")
    @Test
    void getMaker() {
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
    }

    @DisplayName("getPrice 상품 가격을 리턴한다")
    @Test
    void getPrice() {
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
    }

    @DisplayName("getImageUrl은 상품명을 리턴한다")
    @Test
    void getImageUrl() {
        assertThat(product.getImgUrl()).isEqualTo(PRODUCT_IMAGE_URL);
    }
}