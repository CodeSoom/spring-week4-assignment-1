package com.codesoom.assignment.product.ui.dto;

import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductSaveRequestDtoTest {
    private static final String PRODUCT1_NAME = "product1";
    private static final String PRODUCT1_MAKER = "maker1";
    private static final String PRODUCT1_IMAGE = "https://http.cat/599";
    private static final int PRODUCT1_PRICE = 10_000;

    @DisplayName("상품 dto를 상품 entity로 리턴한다 ")
    @Test
    void convertDtoToEntity() {
        ProductSaveRequestDto requestDto = ProductSaveRequestDto.builder()
                .name(PRODUCT1_NAME)
                .maker(PRODUCT1_MAKER)
                .price(PRODUCT1_PRICE)
                .imageUrl(PRODUCT1_IMAGE)
                .build();

        Product product = requestDto.toEntity();

        assertThat(product instanceof Product).isTrue();
    }
}
