package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codesoom.assignment.Dto.CreateProductDto;
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
                .thenReturn(new Product("title"));
        }

        @Test
        @DisplayName("Product를 생성하고 리턴한다.")
        void it_returns_a_product() {
            Product product = new Product("title");
            assertThat(productService.createProduct(product))
                .isInstanceOf(Product.class)
                .matches(output -> "title".equals(output.getTitle()))
                .matches(output -> output.getId() != null);
        }

        @AfterEach
        void tearDown() {
            verify(productRepository)
                .save(argThat(input -> "title".equals(input.getTitle())));
        }
    }
}