package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("ProductDeleteController 클래스")
public class ProductDeleteControllerMockMvcTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("delete 메서드는")
    @Nested
    class Describe_delete {

        @DisplayName("존재하는 상품 id가 주어지면")
        @Nested
        class Context_with_exist_id {

            private Long EXIST_ID;

            @BeforeEach
            void setup() {
                final Product product
                        = new Product("쥐돌이", "캣이즈락스타", BigDecimal.valueOf(4000), "");
                this.EXIST_ID = repository.save(product).getId();
            }

            @DisplayName("해당 상품을 삭제한다.")
            @Test
            void it_delete_product() throws Exception {
                mockMvc.perform(delete("/products/" + EXIST_ID))
                        .andExpect(status().isNoContent());
            }
        }

        @DisplayName("존재하지 않는 상품 id가 주어지면")
        @Nested
        class Context_with_not_exist_id {

            private final Long NOT_EXIST_ID = 100L;

            @BeforeEach
            void setup() {
                if (repository.existsById(NOT_EXIST_ID)) {
                    repository.deleteById(NOT_EXIST_ID);
                }
            }

            @DisplayName("404 not found를 보낸다.")
            @Test
            void will_throw_not_found_exception() throws Exception {
                mockMvc.perform(delete("/products/" + NOT_EXIST_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
