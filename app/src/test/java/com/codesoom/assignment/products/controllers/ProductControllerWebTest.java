package com.codesoom.assignment.products.controllers;

import com.codesoom.assignment.products.application.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class list_메서드는 {
        @Test
        @DisplayName("200 코드를 반환한다")
        void it_responses_200() throws Exception {
            mockMvc.perform(
                            get("/products")
                    )
                    .andExpect(status().isOk());
        }
    }
}
