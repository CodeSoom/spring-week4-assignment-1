package com.codesoom.assignment.products.controllers;


import com.codesoom.assignment.products.application.ProductService;
import com.codesoom.assignment.products.domain.Product;
import com.codesoom.assignment.products.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

class ProductControllerTest {
    private ProductController productController;
    private ProductService productService;

    @BeforeEach
    void setUpVariable() {
        ProductRepository productRepository = spy(ProductRepository.class);
        productService = new ProductService(productRepository);
        productController = new ProductController(productService);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 장난감_목록_조회_메서드는 {

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
                given(productService.getTasks()).willReturn(products);

                assertThat(productController.list()).isNotEmpty();
            }
        }
    }
}
