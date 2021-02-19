package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.ui.dto.ProductResponseDto;
import com.codesoom.assignment.product.ui.dto.ProductSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("ProductService 클래스")
public class ProductServiceBddTest {
    private static final Long PRODUCT1_ID = 1L;
    private static final String PRODUCT1_NAME = "product1";
    private static final String PRODUCT1_MAKER = "maker1";
    private static final String PRODUCT1_IMAGE = "https://http.cat/599";
    private static final int PRODUCT1_PRICE = 10_000;

    @Autowired
    ProductService productService;

    @Nested
    @DisplayName("getProducts 메서드는")
    class Describe_getProducts {

        @Nested
        @DisplayName("등록된 제품이 존재하면")
        class Context_with_products {
            @BeforeEach
            void setUp() {
                ProductSaveRequestDto requestDto = saveRequestDto();
                productService.createProduct(requestDto);
            }

            @DisplayName("등록된 상품 목록을 리턴한다")
            @Test
            void It_return_products() {
                assertThat(productService.getProducts()).hasSize(1);
                assertThat(productService.getProducts()).contains(savedResponseDto());
            }
        }

        @Nested
        @DisplayName("등록된 제품이 존재하지 않으면")
        class Context_without_products {
            @DisplayName("비어있는 상품 목록을 리턴한다")
            @Test
            void It_return_empty_products() {
                assertThat(productService.getProducts()).isEmpty();
            }
        }
    }

    private ProductSaveRequestDto saveRequestDto() {
        return ProductSaveRequestDto.builder()
                .name(PRODUCT1_NAME)
                .maker(PRODUCT1_MAKER)
                .price(PRODUCT1_PRICE)
                .imageUrl(PRODUCT1_IMAGE)
                .build();
    }

    private ProductResponseDto savedResponseDto() {
        return ProductResponseDto.builder()
                .id(PRODUCT1_ID)
                .name(PRODUCT1_NAME)
                .maker(PRODUCT1_MAKER)
                .price(PRODUCT1_PRICE)
                .imageUrl(PRODUCT1_IMAGE)
                .build();
    }
}
