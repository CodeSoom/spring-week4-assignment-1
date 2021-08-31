package com.codesoom.assignment.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductNotFoundExceptionTest {

    @Test
    @DisplayName("에러 객체 생성 시 메시지가 올바른지 검증한다")
    void verify_error_message_when_it_created() {
        long id = 1L;
        ProductNotFoundException exception = new ProductNotFoundException(id);

        String message = exception.getMessage();
        String format = ProductNotFoundException.FORMAT;

        assertThat(message).isEqualTo(format.formatted(id));
    }
}
