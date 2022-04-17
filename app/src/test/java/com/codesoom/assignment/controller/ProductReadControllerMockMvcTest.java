package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ProductReadController 클래스")
public class ProductReadControllerMockMvcTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("findAll 메서드는")
    @Nested
    class Describe_find_all {

        private final Product SAVED_PRODUCT
                = new Product("쥐돌이", "캣이즈락스타", BigDecimal.valueOf(4000), "");

        @BeforeEach
        void setup() {
            repository.save(SAVED_PRODUCT);
        }

        @AfterEach
        void cleanup() {
            repository.deleteAll();
        }

        @DisplayName("저장된 모든 상품을 반환한다.")
        @Test
        void will_return_all_products() throws Exception {
            final MvcResult result = mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andReturn();

            final List<Product> products
                    = objectMapper.readValue(result.getResponse().getContentAsByteArray(), List.class);
            assertThat(products).isNotEmpty();
        }
    }

    @DisplayName("findById 메서드는")
    @Nested
    class Describe_find_by_id {

        @DisplayName("존재하는 id로 요청하면")
        @Nested
        class Context_with_exist_id {

            private Long EXIST_ID;

            @BeforeEach
            void setup() {
                final Product product
                        = new Product("쥐돌이", "캣이즈락스타", BigDecimal.valueOf(4000), "");
                this.EXIST_ID = repository.save(product).getId();
            }

            @AfterEach
            void cleanup() {
                repository.deleteAll();
            }

            @DisplayName("찾은 상품을 반환한다.")
            @Test
            void will_return_found_product() throws Exception {
                final MvcResult result = mockMvc.perform(get("/products/" + EXIST_ID))
                        .andExpect(status().isOk())
                        .andReturn();
                final Product product = objectMapper.readValue(result.getResponse().getContentAsByteArray(), Product.class);
                assertThat(product.getId()).isEqualTo(EXIST_ID);
            }
        }

        @DisplayName("존재하지 않는 id로 요청하면")
        @Nested
        class Context_with_not_exist_id {

            private Long NOT_EXIST_ID = 100L;

            @BeforeEach
            void setup() {
                if (repository.existsById(NOT_EXIST_ID)) {
                    repository.deleteById(NOT_EXIST_ID);
                }
            }

            @DisplayName("404 not found를 던진다.")
            @Test
            void will_return_found_product() throws Exception {
                mockMvc.perform(get("/products/" + NOT_EXIST_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }

}
