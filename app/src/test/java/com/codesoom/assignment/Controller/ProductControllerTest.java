package com.codesoom.assignment.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ProductController 클래스")
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @InjectMocks
    ProductController productController;

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list {
        @Nested
        @DisplayName("저장된 Product가 없다면")
        class Context_product_empty {
            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_returns_a_empty_list() {
                assertThat(productController.list()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Descirbe_create {
        @Test
        @DisplayName("product를 생성한다.")
        void it_returns_a_product() {
            CreateProductDto createProductDto = new CreateProductDto("title");
            assertThat(productController.create(createProductDto))
                .matches(output -> "title".equals(output.getTitle()));
        }
        

    }
}
