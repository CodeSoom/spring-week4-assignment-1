package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CatToyController 클래스의")
public class CatToyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("POST /toys 요청은")
    class Describe_create {
        @Nested
        @DisplayName("장난감이 주어지면")
        class Context_with_catToy {
            @Test
            @DisplayName("장난감과 상태코드 201을 응답한다")
            void It_returns_catToy_and_statusCreated() throws Exception {
                Map<String, String> input = new HashMap<>();
                input.put("name", "고양이");
                input.put("maker", "허먼밀러");
                input.put("price", "90000");
                input.put("url", "url");

                mockMvc.perform(post("/toys")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input)))
                        .andExpect(jsonPath("$.name").value("고양이"))
                        .andExpect(jsonPath("$.maker").value("허먼밀러"))
                        .andExpect(jsonPath("$.price").value(90000))
                        .andExpect(jsonPath("$.url").value("url"))
                        .andExpect(status().isCreated());
            }
        }
    }
}
