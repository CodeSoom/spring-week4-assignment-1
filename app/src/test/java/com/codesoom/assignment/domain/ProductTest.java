package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private static final String NAME = "뱀 장난감";

    private static final String MAKER = "야옹이네 장난감";

    private static final Integer PRICE = 3000;

    private static final String IMAGE = "https://bit.ly/3qzXRME";

    Product product;

    @Test
    @DisplayName("장난감이 정상적으로 등록되었는지 확인하기 위해 상품 정보를 확인합니다")
    void productTest() {
        product = Product.builder()
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .image(IMAGE)
                .build();

        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImage()).isEqualTo(IMAGE);
    }
}
