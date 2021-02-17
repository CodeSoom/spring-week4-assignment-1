package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebMvcTest(ProductController.class)
@DisplayName("ProductController 테스트")
class ProductControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list {

        @Test
        @DisplayName("OK를 리턴한다")
        void itReturnsOKHttpStatus() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk());
        }
    }
}
