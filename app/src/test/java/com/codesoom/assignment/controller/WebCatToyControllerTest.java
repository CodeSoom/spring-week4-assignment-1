package com.codesoom.assignment.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CatToyController.class)
@AutoConfigureMockMvc
@DisplayName("고양이 장난감에 대한 HTTP 요청")
public class WebCatToyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Nested
    @DisplayName("GET - /products 요청시")
    class Describe_list {

        @Nested
        @DisplayName("고양이 장난감 상품 수 만큼")
        class Context_givenCount {

            final int givenSize = 10;

            @Test
            @DisplayName("고양이 장난감 상품을 응답한다. [200]")
            void it_response_products_and_http_status_200() throws Exception {

                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(givenSize)));
            }
        }
    }
}
