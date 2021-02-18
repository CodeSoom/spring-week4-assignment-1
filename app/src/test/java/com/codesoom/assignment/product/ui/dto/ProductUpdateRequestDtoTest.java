package com.codesoom.assignment.product.ui.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductUpdateRequestDtoTest {
    private static final String UPDATE_DTO_TO_STRING = "ProductUpdateRequestDto.ProductUpdateRequestDtoBuilder(name=null, maker=null, price=0, imageUrl=null)";

    @DisplayName("상품 갱신 DTO의 ToString을 리턴한다")
    @Test
    void builderToString() {
        assertThat(ProductUpdateRequestDto.builder().toString()).isEqualTo(UPDATE_DTO_TO_STRING);
    }
}
