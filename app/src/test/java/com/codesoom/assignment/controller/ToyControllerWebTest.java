package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Toy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Nested
@DisplayName("ToyController 클래스의")
@SpringBootTest
@AutoConfigureMockMvc
class ToyControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void controllerSetUp() {
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("createToy 메소드는")
    class DescribeCreateToy {

        @Nested
        @DisplayName("장난감에 대한 정보를 받으면")
        class ContextWithToyInfo {

            @Test
            @DisplayName("정보를 저장하고 반환한다")
            void ItReturnsToy() throws Exception {
                Toy toy = new Toy.Builder("장난감", "브랜드")
                        .price(1000.0)
                        .build();
                String body = objectMapper.writeValueAsString(toy);
                mockMvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                        .andDo(print())
                        .andExpect(status().isCreated());
            }
        }
    }
}
