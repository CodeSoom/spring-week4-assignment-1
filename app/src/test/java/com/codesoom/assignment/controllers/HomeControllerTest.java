package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("HomeController 클래스")
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final String SHOP_NAME = "고양이 장난감 가게";

    @Nested
    @DisplayName("home 메소드는")
    class Describe_home {
        @Test
        @DisplayName("HTTP Status Code 200 OK로 응답한다")
        void it_responds_with_200_ok() throws Exception {
            mockMvc.perform(get("/"))
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("가게 이름을 반환한다")
        void it_returns_shop_name() throws Exception {
            mockMvc.perform(get("/"))
                .andExpect(content().string(containsString(SHOP_NAME)));
        }
    }
}
