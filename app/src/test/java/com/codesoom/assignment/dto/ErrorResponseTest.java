package com.codesoom.assignment.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ErrorResponse 클래스 테스트")
class ErrorResponseTest {

    @Test
    @DisplayName("getMessage 메소드는 메세지를 반환한다.")
    void getMessage() {
        String message = "에러 메세지";
        ErrorResponse errorResponse = new ErrorResponse(message);
        assertThat(errorResponse.getMessage()).isEqualTo(message);
    }
}