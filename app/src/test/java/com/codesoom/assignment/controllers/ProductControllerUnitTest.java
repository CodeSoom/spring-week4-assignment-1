package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("ProductController Unit 테스트")
public class ProductControllerUnitTest {

    private final String PRODUCT_NAME = "Product";
    private final String CREATE_PREFIX = "Create";
    private final String UPDATE_PREFIX = "Update";
    private final Long EXISTED_ID = 1L;
    private final Long NOT_EXISTED_ID = -1L;

    @MockBean
    ProductService productService;

    @Autowired
    private ProductController productController;

    @Nested
    @DisplayName("getProductList 메소드는")
    class DescribeGetProductList {

        @Nested
        @DisplayName("등록된 프로덕트가 있으면")
        class ContextWithExistedProducts {

            @BeforeEach
            void prepare() {
                List<Product> productList = new ArrayList<>();
                Product product = new Product();
                product.setName(PRODUCT_NAME);
                productList.add(product);
                when(productService.getProductList()).thenReturn(productList);
            }

            @Test
            @DisplayName("프로덕트 목록을 리턴한다")
            void itReturnsProductList() {
                assertThat(productController.getProductList()).isNotEmpty();
                verify(productService, atLeastOnce()).getProductList();
            }
        }

        @Nested
        @DisplayName("등록된 프로덕트가 없으면")
        class ContextWithNotExistedProducts {

            @BeforeEach
            void prepare() {
                List<Product> productList = new ArrayList<>();
                when(productService.getProductList()).thenReturn(productList);
            }

            @Test
            @DisplayName("빈 프로덕트 목록을 리턴한다")
            void itReturnsEmptyProductList() {
                assertThat(productController.getProductList()).isEmpty();
                verify(productService, atLeastOnce()).getProductList();
            }
        }
    }

    @Nested
    @DisplayName("getDetailProduct 메소드는")
    class DescribeGetDetailProduct {

        @Nested
        @DisplayName("productId가 존재 하면")
        class ContextWithExistedProductId {

            @BeforeEach
            void prepareProduct() {
                Product product = new Product();
                product.setName(PRODUCT_NAME);
                when(productService.createProduct(product)).thenReturn(product);
                given(productService.getProduct(EXISTED_ID)).willReturn(product);
            }

            @Test
            @DisplayName("프로덕트 상세정보를 리턴한다")
            void itReturnsDetailProduct() {
                assertThat(productController.getDetailProduct(EXISTED_ID)).isNotNull();
                assertThat(productController.getDetailProduct(EXISTED_ID).getName()).isEqualTo(PRODUCT_NAME);
                verify(productService, atLeastOnce()).getProduct(EXISTED_ID);
            }
        }

        @Nested
        @DisplayName("productId가 존재 하지 않으면")
        class ContextWithNotExistedProductId {

            @Test
            @DisplayName("ProductNotFoundException 예외를 리턴한다")
            void itReturnsProductNotFoundException() {
                given(productService.getProduct(NOT_EXISTED_ID)).willThrow(ProductNotFoundException.class);
                assertThatThrownBy(() -> productController.getDetailProduct(NOT_EXISTED_ID)).isInstanceOf(ProductNotFoundException.class);
                verify(productService, atLeastOnce()).getProduct(NOT_EXISTED_ID);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class DescribeCreateProduct {

        @Nested
        @DisplayName("새 Product 이면")
        class ContextWithNewProduct {

            @Test
            @DisplayName("새로 만든 프로덕트를 리턴한다")
            void itReturnsNewProduct() {
                Product product = new Product();
                product.setName(CREATE_PREFIX + PRODUCT_NAME);

                when(productService.createProduct(product)).thenReturn(product);
                assertThat(productController.createProduct(product).getName()).isEqualTo(CREATE_PREFIX + PRODUCT_NAME);
                verify(productService).createProduct(product);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class DescribeUpdateProduct {

        @Nested
        @DisplayName("productId가 존재 하고 업데이트 할 product가 있다면")
        class ContextWithExistedProductIdAndProductForUpdate {

            @Test
            @DisplayName("업데이트 된 프로덕트를 리턴한다")
            void itReturnsUpdatedProduct() {
                Product product = new Product();
                product.setName(UPDATE_PREFIX + PRODUCT_NAME);
                when(productService.updateProduct(eq(EXISTED_ID), any(Product.class))).thenReturn(product);
                assertThat(productController.updateProduct(EXISTED_ID, product).getName()).isEqualTo(UPDATE_PREFIX + PRODUCT_NAME);
                verify(productService).updateProduct(eq(EXISTED_ID), any(Product.class));
            }
        }

        @Nested
        @DisplayName("productId가 존재 하지 않으면")
        class ContextWithNotExistedProductId {

            @Test
            @DisplayName("ProductNotFoundException 예외를 리턴한다")
            void itReturnsProductNotFoundException() {
                when(productService.updateProduct(eq(NOT_EXISTED_ID), any(Product.class))).thenThrow(ProductNotFoundException.class);
                assertThatThrownBy(() -> productController.updateProduct(NOT_EXISTED_ID, new Product())).isInstanceOf(ProductNotFoundException.class);
                verify(productService).updateProduct(eq(NOT_EXISTED_ID), any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class DescribeDeleteProduct {

        @Nested
        @DisplayName("productId가 존재 하면")
        class ContextWithExistedProductId {

            @Test
            @DisplayName("프로덕트를 삭제한다")
            void itDeletesProduct() {
                Product product = new Product();
                when(productService.createProduct(product)).thenReturn(product);
                doNothing().when(productService).deleteProduct(EXISTED_ID);
                productController.deleteProduct(EXISTED_ID);
                verify(productService).deleteProduct(EXISTED_ID);
            }
        }

        @Nested
        @DisplayName("productId가 존재 하지 않으면")
        class ContextWithNotExistedProductId {

            @Test
            @DisplayName("ProductNotFoundException 예외를 리턴한다")
            void itReturnsProductNotFoundException() {
                doThrow(ProductNotFoundException.class).when(productService).deleteProduct(NOT_EXISTED_ID);
                assertThatThrownBy(() -> productController.deleteProduct(NOT_EXISTED_ID)).isInstanceOf(ProductNotFoundException.class);
                verify(productService).deleteProduct(NOT_EXISTED_ID);
            }
        }
    }
}
