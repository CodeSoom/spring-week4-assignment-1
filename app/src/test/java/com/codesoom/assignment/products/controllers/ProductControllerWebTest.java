package com.codesoom.assignment.products.controllers;

import com.codesoom.assignment.products.application.ProductService;
import com.codesoom.assignment.products.domain.Product;
import com.codesoom.assignment.util.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.codesoom.assignment.support.ProductFieldFixture.TEST_NOT_EXIST;
import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class list_메서드는 {
        @Test
        @DisplayName("200 코드를 반환한다")
        void it_responses_200() throws Exception {
            mockMvc.perform(
                            get("/products")
                    )
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class detail_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @Test
            @DisplayName("200 코드를 반환한다")
            void it_responses_200() throws Exception {
                Product productSource = productService.createProduct(TOY_1.요청_데이터_생성());

                mockMvc.perform(
                                get("/products/" + productSource.getId())
                        )
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("404 코드를 반환한다")
            void it_responses_404() throws Exception {
                mockMvc.perform(
                                get("/products/" + TEST_NOT_EXIST.ID())
                        )
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class create_메서드는 {
        @Test
        @DisplayName("201 코드를 반환한다")
        void it_returns_201() throws Exception {
            mockMvc.perform(
                            post("/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(JsonUtil.writeValue(TOY_1.요청_데이터_생성()))
                    )
                    .andExpect(status().isCreated());
        }
    }
}
