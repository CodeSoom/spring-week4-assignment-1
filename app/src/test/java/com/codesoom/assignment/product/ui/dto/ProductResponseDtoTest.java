package com.codesoom.assignment.product.ui.dto;

import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseDtoTest {
    private static Long PRODUCT1_ID = 1L;
    private static Long PRODUCT2_ID = 2L;

    @DisplayName("id 기준으로 상품 요청 객체의 해쉬값를 비교한다")
    @Test
    void compareHashcode() {
        ProductResponseDto dto1 = getProductResponseDto(PRODUCT1_ID);
        ProductResponseDto dto2 = getProductResponseDto(PRODUCT1_ID);

        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());

        ProductResponseDto dto3 = getProductResponseDto(PRODUCT2_ID);
        assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
    }

    @DisplayName("id 기준으로 상품 요청 객체를 비교한다.")
    @Test
    void equalsWithSameId() {
        ProductResponseDto dto1 = getProductResponseDto(PRODUCT1_ID);
        ProductResponseDto dto2 = getProductResponseDto(PRODUCT1_ID);

        assertThat(dto1.equals(dto1)).isTrue();
        assertThat(dto1.equals(dto2)).isTrue();

        ProductResponseDto dto3 = getProductResponseDto(PRODUCT2_ID);
        assertThat(dto3.equals(dto2)).isFalse();
    }

    @DisplayName("서로 다른 객체 비교후, 다르면 false를 리턴한다")
    @Test
    void equalsWithDifferentObject() {
        ProductResponseDto dto1 = getProductResponseDto(PRODUCT1_ID);
        Product product = Product.builder().build();

        assertThat(dto1.equals(product)).isFalse();
    }

    private ProductResponseDto getProductResponseDto(Long productId) {
        return ProductResponseDto.builder()
                .id(productId)
                .build();
    }
}
