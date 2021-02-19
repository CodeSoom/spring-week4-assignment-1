package com.codesoom.assignment.product.ui.dto;

import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductSaveRequestDtoTest {
    @DisplayName("상품 dto를 상품 entity로 리턴한다 ")
    @Test
    void convertDtoToEntity() {
        ProductSaveRequestDto requestDto = ProductSaveRequestDto.builder()
                .build();

        Product product = requestDto.toEntity();

        assertThat(product instanceof Product).isTrue();
    }
}
