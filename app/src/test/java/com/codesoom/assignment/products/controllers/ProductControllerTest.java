package com.codesoom.assignment.products.controllers;


import com.codesoom.assignment.exception.products.ProductNotFoundException;
import com.codesoom.assignment.products.application.ProductService;
import com.codesoom.assignment.products.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codesoom.assignment.support.ProductFieldFixture.TEST_EXIST;
import static com.codesoom.assignment.support.ProductFieldFixture.TEST_NOT_EXIST;
import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductController 유닛 테스트")
class ProductControllerTest {
    private ProductController productController;
    private ProductService productService;

    @BeforeEach
    void setUpVariable() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class list_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_없을_때 {
            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_empty_list() {
                List<Product> products = productController.list();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_있을_때 {
            @Test
            @DisplayName("비어있지 않은 리스트를 리턴한다")
            void it_returns_list() {
                List<Product> products = new ArrayList<>();
                products.add(TOY_1.생성());
                given(productService.getProducts()).willReturn(products);

                assertThat(productController.list()).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class detail_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                given(productService.getProduct(TEST_NOT_EXIST.ID()))
                        .willThrow(ProductNotFoundException.class);

                assertThatThrownBy(() -> productController.detail(TEST_NOT_EXIST.ID()))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productService).getProduct(TEST_NOT_EXIST.ID());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @Test
            @DisplayName("해당 id의 장난감 정보를 리턴한다")
            void it_returns_product() {
                given(productService.getProduct(TEST_EXIST.ID()))
                        .willReturn(TOY_1.생성(TEST_EXIST.ID()));

                Product product = productController.detail(TEST_EXIST.ID());

                verify(productService).getProduct(TEST_EXIST.ID());

                assertThat(product).isNotNull();
                assertThat(product.getId()).isEqualTo(TEST_EXIST.ID());
            }
        }
    }
}
