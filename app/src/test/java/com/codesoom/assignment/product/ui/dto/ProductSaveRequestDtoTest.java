package com.codesoom.assignment.product.ui.dto;

import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductSaveRequestDtoTest {
    @DisplayName("상품 요청 객체를 상품 정보로 변경시, 타입이 상품과 동일하면 true를 반환한다")
    @Test
    void convertDtoToEntity() {
        ProductSaveRequestDto requestDto = ProductSaveRequestDto.builder()
                .build();

        Product product = requestDto.toEntity();

        assertThat(product instanceof Product).isTrue();
    }
}
