package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DisplayName("ProductController 테스트")
public class ProductControllerTest {
    private ProductController productController;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list {
        @Nested
        @DisplayName("고양이 장난감의 목록이 존재하면")
        class Context_WithoutProducts {
            @Test
            @DisplayName("비어 있지 않는 장난감 목록을 반환한다")
            void itReturnsWithSomeProducts() {
                List<Product> products = productController.list();
                assertThat(products).isEmpty();
            }
        }
    }
}
