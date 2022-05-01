package com.codesoom.assignment.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스는")
class ProductTest {
    private static final Integer PRODUCT_PRICE = 1000;
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "maker";
    private static final String PRODUCT_IMAGE_URL = "imageUrl";

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product
                .Builder(PRODUCT_PRICE, PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .imageUrl(PRODUCT_IMAGE_URL)
                .build();
    }

    @Test
    @DisplayName("price 를 반환한다")
    void it_return_price() {
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
    }

    @Test
    @DisplayName("name 을 반환한다")
    void it_return_name() {
        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
    }

    @Test
    @DisplayName("maker 를 반환한다")
    void it_return_maker() {
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
    }

    @Test
    @DisplayName("imageUrl 을 반환한다")
    void it_return_imageUrl() {
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL);
    }
}
