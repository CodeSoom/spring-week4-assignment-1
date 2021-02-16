package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
@DisplayName("HelloController 클래스")
public class HelloControllerWebMvcTest {
    @Autowired
    MockMvc mockMvc;

    @Nested
    @DisplayName("만약 /의 경로가 주어진다면")
    class ContextWithRootUrl {
        private final String rootUrl = "/";

        @Test
        @DisplayName("OK를 반환한다")
        void itReturnsWithOKHttpStatus() throws Exception {
            mockMvc.perform(get(rootUrl))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Hello, world!")));
        }
    }
}
