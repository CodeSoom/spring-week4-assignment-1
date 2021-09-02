package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.codesoom.assignment.domain.ProductConstant.TITLE;

import java.util.Optional;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ProductService 클래스")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Nested
    @DisplayName("create 메서드는")
    class Describe_createProduct {
        @BeforeEach
        void setUp() {
            when(productRepository.save(any(Product.class)))
                .thenReturn(new Product(TITLE));
        }

        @Test
        @DisplayName("Product를 생성하고 리턴한다.")
        void it_returns_a_product() {
            assertThat(productService.createProduct(new Product(TITLE)))
                .isInstanceOf(Product.class);
        }

        @AfterEach
        void tearDown() {
            verify(productRepository)
                .save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("detailProduct 메서드는")
    class Describe_detailProduct {
        @Nested
        @DisplayName("Product를 찾을 수 있다면")
        class Context_find_success {
            @BeforeEach
            void setUp() {
                when(productRepository.findById(anyLong()))
                    .thenReturn(Optional.of(new Product(TITLE)));
            }

            @Test
            @DisplayName("찾은 Product를 리턴한다.") void it_returns_a_find_product() {
                assertThat(productService.detailProduct(anyLong()))
                    .isInstanceOf(Product.class);
            }
        }

        @Nested
        @DisplayName("Product를 찾을 수 없다면")
        class Context_find_fail {
            @BeforeEach
            void setUp() {
                when(productRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다.")
            void it_throws_a_productNotFoundException() {
                assertThatThrownBy(() -> productService.detailProduct(anyLong()))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @AfterEach
        void tearDown() {
            verify(productRepository)
                .findById(any(Long.class));
        }
    }
}