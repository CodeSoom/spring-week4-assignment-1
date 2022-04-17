package com.codesoom.assignment.application;

import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductCommandService 클래스")
@DataJpaTest
class ProductCommandServiceTest {

    @Autowired
    private ProductRepository productRepository;

    private ProductCommandService productCommandService;

    private Long NOT_SAVED_ID = 100L;
    private static final String TEST_PRODUCT_MAKER = "MAKER";
    private static final String TEST_PRODUCT_NAME = "NAME";
    private static final BigDecimal TEST_PRODUCT_PRICE = BigDecimal.TEN;
    private static final String TEST_PRODUCT_IMAGE_URL = "/image.png";

    @BeforeEach
    void setUp() {
        productCommandService = new ProductCommandService(productRepository);
    }


    @Test
    @DisplayName("create 메소드는 상품을 저장한다.")
    void create() {
        int oldSize = productRepository.findAll().size();
        ProductDto resource = new ProductDto.ProductDtoBuilder()
                                .name("New " + TEST_PRODUCT_NAME)
                                .maker(TEST_PRODUCT_MAKER)
                                .price(TEST_PRODUCT_PRICE)
                                .imageUrl(TEST_PRODUCT_IMAGE_URL)
                                .build();

        Product newProduct = productCommandService.create(resource);

        assertThat(productRepository.findById(newProduct.getId())).isNotEmpty();
        assertThat(productRepository.getOne(newProduct.getId()).getName()).isEqualTo("New " + TEST_PRODUCT_NAME);
        assertThat(productRepository.findAll().size()).isEqualTo(oldSize + 1);
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateTask {

        private ProductDto resource;
        private final String updatedName = "Updated " + TEST_PRODUCT_NAME;

        @BeforeEach
        void prepare() {
            resource = new ProductDto.ProductDtoBuilder()
                    .name(updatedName)
                    .maker(TEST_PRODUCT_MAKER)
                    .price(TEST_PRODUCT_PRICE)
                    .imageUrl(TEST_PRODUCT_IMAGE_URL)
                    .build();
        }

        @Nested
        @DisplayName("아이디가 상품 목록에 존재한다면")
        class Context_with_a_valid_id {

            private Long productId;

            @BeforeEach
            void prepareProduct() {
                Product product = productCommandService.create(new ProductDto());
                productId = product.getId();
            }

            @Test
            @DisplayName("상품을 수정하여 반환한다.")
            void it_returns_a_updated_product() {
                Product product = productCommandService.updateProduct(productId, resource);
                assertThat(product.getName()).isEqualTo(updatedName);
            }
        }

        @Nested
        @DisplayName("아이디가 상품 목록에 존재하지 않는다면")
        class Context_with_a_invalid_id {
            @Test
            @DisplayName("예외를 던진다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> productCommandService.updateProduct(NOT_SAVED_ID, resource))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {

        @Nested
        @DisplayName("아이디가 상품 목록에 존재한다면")
        class Context_with_a_valid_id {

            private Long productId;

            @BeforeEach
            void prepareProduct() {
                Product product = productCommandService.create(new ProductDto());
                productId = product.getId();
            }

            @Test
            @DisplayName("상품을 삭제한다.")
            void it_returns_a_deleted_product() {
                int oldSize = productRepository.findAll().size();
                productCommandService.deleteProduct(productId);

                int newSize = productRepository.findAll().size();

                assertThatThrownBy(() -> productCommandService.deleteProduct(productId))
                        .isInstanceOf(ProductNotFoundException.class);
                assertThat(productRepository.findById(productId)).isEmpty();
                assertThat(newSize).isEqualTo(oldSize - 1);
            }
        }

        @Nested
        @DisplayName("아이디가 상품 목록에 존재하지 않는다면")
        class Context_with_a_invalid_id {
            @Test
            @DisplayName("예외를 던진다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> productCommandService.deleteProduct(NOT_SAVED_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}