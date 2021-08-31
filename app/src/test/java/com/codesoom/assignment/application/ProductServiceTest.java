package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.ProductRepository;

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
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Nested
    @DisplayName("create 메서드는")
    class Describe_createProduct {
        @Test
        @DisplayName("Product를 생성하고 리턴한다.")
        void it_returns_a_product() {
            CreateProductDto createProductDto = new CreaeteProductDto("title");
            assertThat(productService.createProduct(createProductDto))
                .isInstanceOf(Product.class)
                .matches(output -> "title".equals(output.getTitle()));
        }
    }
}
