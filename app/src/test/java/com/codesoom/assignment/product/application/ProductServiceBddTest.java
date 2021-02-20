package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.infra.ProductRepository;
import com.codesoom.assignment.product.ui.dto.ProductResponseDto;
import com.codesoom.assignment.product.ui.dto.ProductSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@DisplayName("ProductService 클래스")
public class ProductServiceBddTest {
    private static final Long PRODUCT1_ID = 1L;
    private static final Long NOT_EXIST_ID = -1L;
    private static final String PRODUCT1_NAME = "product1";
    private static final String PRODUCT1_MAKER = "maker1";
    private static final String PRODUCT1_IMAGE = "https://http.cat/599";
    private static final int PRODUCT1_PRICE = 10_000;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @AfterEach
    public void cleanup() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_createProduct {
        ProductSaveRequestDto requestDto;

        @BeforeEach
        void setUp() {
            requestDto = saveRequestDto();
        }

        @DisplayName("새로운 상품이 추가되고, 상품 정보를 리턴한다")
        @Test
        void it_returns_product() {
            ProductResponseDto actual = productService.createProduct(requestDto);

            assertAll(
                    () -> assertThat(productService.getProducts()).isNotEmpty(),
                    () -> assertThat(actual.getId()).isNotNull(),
                    () -> assertThat(actual.getImageUrl()).isEqualTo(PRODUCT1_IMAGE),
                    () -> assertThat(actual.getMaker()).isEqualTo(PRODUCT1_MAKER),
                    () -> assertThat(actual.getName()).isEqualTo(PRODUCT1_NAME),
                    () -> assertThat(actual.getPrice()).isEqualTo(PRODUCT1_PRICE)
            );
        }
    }

    @Nested
    @DisplayName("getProducts 메서드는")
    class Describe_getProducts {

        @Nested
        @DisplayName("등록된 상품이 존재하면")
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
            }
        }

        @Nested
        @DisplayName("등록된 상품이 존재하지 않으면")
        class Context_without_products {

            @DisplayName("비어있는 상품 목록을 리턴한다")
            @Test
            void It_return_empty_products() {
                assertThat(productService.getProducts()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {
        Long givenId;

        @Nested
        @DisplayName("등록된 상품 id가 존재하면")
        class Context_with_exist_product_id {

            @BeforeEach
            void setUp() {
                ProductSaveRequestDto requestDto = saveRequestDto();
                ProductResponseDto savedProduct = productService.createProduct(requestDto);
                givenId = savedProduct.getId();
            }

            @DisplayName("등록된 상품 id로 찾고자하는 상품을 리턴한다")
            @Test
            void It_return_product() {
                assertThat(productService.getProduct(givenId)).isEqualTo(savedResponseDto());
            }
        }

        @Nested
        @DisplayName("등록된 상품 id가 존재하지 않으면")
        class Context_without_products {

            @BeforeEach
            void setUp() {
                givenId = NOT_EXIST_ID;
            }

            @DisplayName("예외가 발생한다.")
            @Test
            void It_throws_exception() {
                assertThatExceptionOfType(ProductNotFoundException.class)
                        .isThrownBy(() -> productService.getProduct(givenId));
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
