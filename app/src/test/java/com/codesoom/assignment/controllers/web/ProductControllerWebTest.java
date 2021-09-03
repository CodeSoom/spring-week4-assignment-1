package com.codesoom.assignment.controllers.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("POST /products 요청은")
    class Describe_postProduct {

        @Nested
        @DisplayName("상품 객체가 주어지면")
        class Context_givenProduct {

            Product product = Product.builder()
                .name("name")
                .maker("maker")
                .price(10000L)
                .imageUrl("imageUrl")
                .build();

            @Test
            @DisplayName("새 상품을 생성하고 201을 응답한다")
            void it_create_a_product_and_response_201() throws Exception {
                mockMvc.perform(post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(product.stringify()))
                    .andExpect(status().isCreated());
            }
        }
    }

    @Nested
    @DisplayName("GET /products 요청은")
    class Describe_getProduct {

        @Test
        @DisplayName("모든 상품을 반환한다")
        void it_returns_all_products_with_200() throws Exception {
            mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("GET /products/{id} 요청은")
    class Describe_getProductWithId {

        @Nested
        @DisplayName("존재하는 id일 경우")
        class Context_existId {

        }

        @Nested
        @DisplayName("존재하지 않는 id일 경우")
        class Context_notExistId {

        }
    }


    @Nested
    @DisplayName("PATCH /products/{id} 요청은")
    class Describe_patchProductWithId {

        @Nested
        @DisplayName("존재하는 id일 경우")
        class Context_existId {

        }

        @Nested
        @DisplayName("존재하지 않는 id일 경우")
        class Context_notExistId {

        }
    }


    @Nested
    @DisplayName("DELETE /products/{id} 요청은")
    class Describe_deleteProductWithId {

        @Nested
        @DisplayName("존재하는 id일 경우")
        class Context_existId {

        }

        @Nested
        @DisplayName("존재하지 않는 id일 경우")
        class Context_notExistId {

        }
    }
}
