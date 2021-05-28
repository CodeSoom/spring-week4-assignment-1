package com.codesoom.assignment.web.controller;

import com.codesoom.assignment.core.application.ProductService;
import com.codesoom.assignment.core.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("ProductController class")
class ProductControllerTest {
    private final String NAME = "고양이 장난감 이름";
    private final String MAKER = "고양이 장난감 브랜드";

    private ProductController productController;
    private ProductService productService;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Nested
    @DisplayName("GET /products")
    class DescribeGetProducts {
        @Nested
        @DisplayName("고양이 장난감 목록이 비었을 때")
        class ContextWithEmptyProducts {
            @BeforeEach
            void prepare() {
                products = new ArrayList<>();

                given(productService.fetchProducts()).willReturn(products);
            }

            @Test
            @DisplayName("빈 배열을 반환한다")
            void returnProducts() {
                List<Product> products = productService.fetchProducts();
                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("고양이 장난감 목록이 있을 때")
        class ContextWithSomeProducts {
            @BeforeEach
            void prepare() {
                products = new ArrayList<>();
                products.add(Product.builder().name(NAME).maker(MAKER).build());

                given(productService.fetchProducts()).willReturn(products);
            }

            @Test
            @DisplayName("모든 장난감을 반환한다")
            void returnProducts() {
                List<Product> products = productService.fetchProducts();
                assertThat(products).isNotEmpty();
                assertThat(products).hasSize(1);

                Product fetchedProduct = products.get(0);
                assertThat(fetchedProduct.getName()).isEqualTo(NAME);
                assertThat(fetchedProduct.getMaker()).isEqualTo(MAKER);
            }
        }
    }
}
