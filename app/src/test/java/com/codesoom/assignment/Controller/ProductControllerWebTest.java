package com.codesoom.assignment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@DisplayName("고양이 장난감 가게 api 서버는")
public class ProductControllerWebTest {
    private static final String EMPTY_LIST = "[]";

    @Autowired
    private MockMvc mockMvc;
    
    @Nested
    @DisplayName("장난감 목록을 요청받은 경우")
    class Context_get_products {
        @Nested
        @DisplayName("저장된 장난감이 없다면")
        class Context_product_empty {
            @Test
            @DisplayName("200과 함께 빈 목록을 리턴한다.")
            void it_returns_a_200_status_code() throws Exception {
                mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(EMPTY_LIST));
            }
        }
    }
}
