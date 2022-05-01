package com.codesoom.assignment.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductDto 는")
class ProductDtoTest {
    private static final Integer PRODUCT_PRICE = 1000;
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "maker";
    private static final String PRODUCT_IMAGE_URL = "imageUrl";

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto
                .Builder(PRODUCT_PRICE, PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .imageUrl(PRODUCT_IMAGE_URL)
                .build();
    }

    @Test
    @DisplayName("price 를 반환한다")
    void it_return_price() {
        assertThat(productDto.getPrice()).isEqualTo(PRODUCT_PRICE);
    }

    @Test
    @DisplayName("name 을 반환한다")
    void it_return_name() {
        assertThat(productDto.getName()).isEqualTo(PRODUCT_NAME);
    }

    @Test
    @DisplayName("maker 를 반환한다")
    void it_return_maker() {
        assertThat(productDto.getMaker()).isEqualTo(PRODUCT_MAKER);
    }

    @Test
    @DisplayName("imageUrl 을 반환한다")
    void it_return_imageUrl() {
        assertThat(productDto.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL);
    }
}