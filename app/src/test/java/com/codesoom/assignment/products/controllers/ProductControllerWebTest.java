package com.codesoom.assignment.products.controllers;

import com.codesoom.assignment.products.domain.Product;
import com.codesoom.assignment.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
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
import static com.codesoom.assignment.support.ProductFixture.TOY_2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 통합 웹 테스트")
public class ProductControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

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
                Product productSource = productController.create(TOY_1.등록_요청_데이터_생성());

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
        void it_responses_201() throws Exception {
            mockMvc.perform(
                            post("/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(JsonUtil.writeValue(TOY_1.등록_요청_데이터_생성()))
                    )
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class update_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {

            private Long fixtureId;

            @BeforeEach
            void setUpCreateFixture() {
                Product productSource = productController.create(TOY_1.등록_요청_데이터_생성());
                fixtureId = productSource.getId();
            }

            @Test
            @DisplayName("200 코드를 반환한다")
            void it_responses_200() throws Exception {
                mockMvc.perform(
                                put("/products/" + fixtureId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(JsonUtil.writeValue(TOY_2.수정_요청_데이터_생성()))
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
                                put("/products/" + TEST_NOT_EXIST.ID())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(JsonUtil.writeValue(TOY_2.수정_요청_데이터_생성()))
                        )
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class delete_메서드는 {
        private Long fixtureId;

        @BeforeEach
        void setUpCreateFixture() {
            Product productSource = productController.create(TOY_1.등록_요청_데이터_생성());
            fixtureId = productSource.getId();
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @Test
            @DisplayName("204 코드를 반환한다")
            void it_responses_204() throws Exception {
                mockMvc.perform(
                                delete("/products/" + fixtureId)
                        )
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("404 코드를 반환한다")
            void it_responses_404() throws Exception {
                mockMvc.perform(
                                delete("/products/" + TEST_NOT_EXIST.ID())
                        )
                        .andExpect(status().isNotFound());
            }
        }
    }
}
