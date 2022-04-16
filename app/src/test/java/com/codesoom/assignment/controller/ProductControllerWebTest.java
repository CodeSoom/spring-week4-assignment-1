package com.codesoom.assignment.controller;

import com.codesoom.assignment.Utf8MockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@Utf8MockMvc
@DisplayName("ProductController Web 계층")
public class ProductControllerWebTest {
    @Autowired MockMvc mockMvc;
    @Autowired ProductController controller;
    private final String productControllerPath = "/products";

    @Nested
    @DisplayName("루트 (/) 경로는")
    class Describe_root_path {
        private final String rootPath = productControllerPath + "/";

        @Nested
        @DisplayName("GET 요청을 받는다면")
        class Context_with_get_request {
            private final ResultActions getRequest;

            public Context_with_get_request() throws Exception {
                this.getRequest = mockMvc.perform(get(rootPath));
            }

            @Nested
            @DisplayName("Product 가 존재하지 않을 때")
            class Context_zero_product {
                @Test
                @DisplayName("빈 리스트를 리턴한다.")
                void it_returns_empty_list() throws Exception {
                    getRequest.andExpect(content().string("[]"));
                }
            }
        }

    }
}
