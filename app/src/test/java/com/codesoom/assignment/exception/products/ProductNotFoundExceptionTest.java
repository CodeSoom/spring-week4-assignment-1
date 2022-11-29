package com.codesoom.assignment.exception.products;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductNotFoundException은")
class ProductNotFoundExceptionTest {
    @Test
    @DisplayName("\"상품이 존재하지 않습니다.\"라는 에러 문구를 던집니다")
    void it_returns_error_message() {
        assertThatThrownBy(() -> { throw new ProductNotFoundException(); })
                .hasMessage("상품이 존재하지 않습니다.");
    }
}
