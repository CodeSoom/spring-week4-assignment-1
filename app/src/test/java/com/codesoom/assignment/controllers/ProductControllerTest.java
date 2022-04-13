package com.codesoom.assignment.controllers;

import com.codesoom.assignment.contexts.ContextProductController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ProductControllerTest 의")
class ProductControllerTest {

    @Nested
    @DisplayName("list() 매소드는")
    class Describe_list {

        @Nested
        @DisplayName("등록된 고양이 물품이 존재하지 않는다면")
        class Context_no_exist_product extends ContextProductController {

            @Test
            @DisplayName("사이즈가 0인 빈 리스트를 반환한다.")
            void it_returns_empty_list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().string("[]"));
            }
        }
    }


}
