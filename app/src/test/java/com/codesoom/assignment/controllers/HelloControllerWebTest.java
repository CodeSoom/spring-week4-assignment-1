package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
@DisplayName("HelloControllerWebTest 클래스")
public class HelloControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("서버가 실행 되면")
    class Describe_sayHello {
        @Test
        @DisplayName("\"Hello, World!\"를 String 타입으로 리턴한다.")
        void sayHello() throws Exception {
            final ResultActions actions = mockMvc.perform(get("/"));

            actions
                    .andExpect(status().isOk());

        }
    }
}
