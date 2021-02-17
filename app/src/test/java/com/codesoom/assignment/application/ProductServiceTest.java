package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("ProductService 테스트")
class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Nested
    @DisplayName("getProducts 메서드는")
    class Describe_getProducts {
        @Test
        @DisplayName("고양이 장난감 목록을 리턴한다")
        void itReturnsListOfProducts() {
            given(productRepository.findAll()).willReturn(new ArrayList<>());

            List<Product> products = productRepository.findAll();

            verify(productRepository).findAll();
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 id가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = 1L;

            @Test
            @DisplayName("주어진 id에 해당하는 고양이 장난감이 주어진다")
            void itReturnsWithExistedProduct() {
                Optional<Product> product = productRepository.findById(givenExistedId);
            }
        }
    }
}