package com.codesoom.assignment;

import com.codesoom.assignment.controller.HomeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
@DisplayName("APP 클래스")
class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("서버 헬스체크를 하면")
    class Describe_checkServerHealth {
        @Test
        @DisplayName("서버가 살아있다고 응답한다")
        void it_returns_server_status() throws Exception {
            mockMvc.perform(get("/healthcheck"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Server is OK"));
        }

        @Test
        void contextLoads() {
            App.main(new String[]{});
        }

    }
}
