package com.codesoom.assignment.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.Dto.CreateProductDto;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ProductController 클래스")
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

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
    class Describe_create {
        @BeforeEach
        void setUp() {
            when(productService.createProduct(any(Product.class)))
                .thenReturn(new Product(1L, "title"));
        }

        @AfterEach
        void tearDown() {
            verify(productService)
                .createProduct(any(Product.class));
        }

        @Test
        @DisplayName("Product를 생성하고 리턴한다.")
        void it_returns_a_product() {
            CreateProductDto createProductDto = new CreateProductDto("title");
            assertThat(productController.create(createProductDto))
                .matches(output -> "title".equals(output.getTitle()))
                .matches(output -> 1L == output.getId());
        }
    }

    @Nested
    @DisplayName("detail 메서드는")
    class Describe_detail {
        @Nested
        @DisplayName("Product를 찾을 수 있으면")
        class Context_find_success {
            @BeforeEach
            void setUp() {
                when(productService.detailProduct(anyLong()))
                    .thenReturn(new Product(1L, "title"));
            }

            @Test
            @DisplayName("찾은 Product를 리턴한다.")
            void it_returns_a_product() {
                assertThat(productController.detail(1L))
                    .matches(output -> id.equals(output.getId()))
                    .matches(output -> "title".equals(output.getTitle()));
            }
        }

        @Nested
        @DisplayName("Product를 찾을 수 없으면")
        class Context_find_fail {
            @BeforeEach
            void setUp() {
                when(productService.detailProduct(anyLong()))
                    .thenThrow(new ProductNotFoundException(anyLong()));
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다.")
            void it_throws_a_productNotFoundException() {
                assertThatThrownBy(() -> productController.detail(1L))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @AfterEach
        void tearDown() {
            verify(productService)
                .detailProduct(anyLong());
        }
    }
}
