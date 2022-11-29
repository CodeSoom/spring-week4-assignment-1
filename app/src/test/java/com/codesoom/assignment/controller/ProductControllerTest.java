package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@DisplayName("ProductController 테스트")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductServiceImpl productService;

    @Nested
    @DisplayName("GET 요청은")
    class Describe_get {
        @Nested
        @DisplayName("id가 없으면")
        class Context_without_segment {
            @Test
            @DisplayName("모든 Product 리스트를 리턴한다")
            void it_return_products() {

            }
        }

        @Nested
        @DisplayName("id를 넘겨받으면")
        class Context_with_id {
            @Test
            @DisplayName("해당하는 id의 Product를 리턴한다")
            void it_return_product() {

            }
        }
    }

    @Nested
    @DisplayName("POST 요청은")
    class Describe_post {
        @Test
        @DisplayName("상품을 새로 만들어 리턴한다")
        void it_return_new_product() {

        }
    }

    @Nested
    @DisplayName("PATCH 요청은")
    class Describe_patch {
        @Test
        @DisplayName("해당하는 id의 product를 업데이트한다")
        void it_update_product() {

        }
    }

    @Nested
    @DisplayName("DELETE 요청은")
    class Describe_delete {
        @Test
        @DisplayName("해당하는 id의 Product를 삭제한다")
        void it_remove_product() {

        }
    }
}
