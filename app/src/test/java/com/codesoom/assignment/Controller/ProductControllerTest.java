package com.codesoom.assignment.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.codesoom.assignment.domain.ProductConstant.TITLE;
import static com.codesoom.assignment.domain.ProductConstant.ID;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.CreateProductDto;
import com.google.common.collect.Lists;

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
            @BeforeEach
            void setUp() {
                when(productService.listProduct())
                    .thenReturn(Lists.newArrayList());
            }

            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_returns_a_empty_list() {
                assertThat(productController.list()).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 Product가 있다면")
        class Context_product_exist {
            @BeforeEach
            void setUp() {
                when(productService.listProduct())
                    .thenReturn(Lists.newArrayList(new Product(TITLE)));
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            void it_returns_a_product_list() {
                assertThat(productController.list()).isNotEmpty();
            }
        }

        @AfterEach
        void tearDown() {
            verify(productService).listProduct();
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @BeforeEach
        void setUp() {
            when(productService.createProduct(any(Product.class)))
                .thenReturn(new Product(TITLE));
        }

        @Test
        @DisplayName("Product를 생성하고 리턴한다.")
        void it_returns_a_product() {
            assertThat(productController.create(new CreateProductDto(TITLE)))
                .isInstanceOf(Product.class);
        }

        @AfterEach
        void tearDown() {
            verify(productService)
                .createProduct(any(Product.class));
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
                    .thenReturn(new Product(TITLE));
            }

            @Test
            @DisplayName("찾은 Product를 리턴한다.")
            void it_returns_a_product() {
                assertThat(productController.detail(ID))
                    .isInstanceOf(Product.class);
            }
        }

        @Nested
        @DisplayName("Product를 찾을 수 없으면")
        class Context_find_fail {
            @BeforeEach
            void setUp() {
                when(productService.detailProduct(anyLong()))
                    .thenThrow(new ProductNotFoundException(ID));
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다.")
            void it_throws_a_productNotFoundException() {
                assertThatThrownBy(() -> productController.detail(ID))
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
