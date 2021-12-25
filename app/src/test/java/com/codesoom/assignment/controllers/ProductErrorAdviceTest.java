package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductErrorAdviceTest {

    @Test
    void handleNotFound(){
        String errorMessage1 = "not found";
        ProductErrorAdvice response1 = new ProductErrorAdvice();


        String errorMessage = "not found";
        ErrorResponse response = new ErrorResponse(errorMessage);
        assertThat(response.getMessage()).isEqualTo(errorMessage);
    }

}
