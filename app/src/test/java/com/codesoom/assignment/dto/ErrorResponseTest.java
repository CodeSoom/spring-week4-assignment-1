package com.codesoom.assignment.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    void getMessage(){
        String errorMessage = "not found";
        ErrorResponse response = new ErrorResponse(errorMessage);
        assertThat(response.getMessage()).isEqualTo(errorMessage);
    }
}
