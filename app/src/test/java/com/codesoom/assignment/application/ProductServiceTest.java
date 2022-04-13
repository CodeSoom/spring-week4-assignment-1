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
import static org.assertj.core.api.Assertions.*;

@DisplayName("ProductService 클래스")
@DataJpaTest
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    private ProductService productService;

    private Long PRODUCT_TEST_ID = 0L;
    private Long NOT_SAVED_ID = 100L;
    private static final String TEST_PRODUCT_MAKER = "MAKER";
    private static final String TEST_PRODUCT_NAME = "NAME";
    private static final BigDecimal TEST_PRODUCT_PRICE = BigDecimal.TEN;
    private static final String TEST_PRODUCT_IMAGE_URL = "/image.png";

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);

        productDto = new ProductDto.ProductDtoBuilder()
                        .name(TEST_PRODUCT_NAME)
                        .maker(TEST_PRODUCT_MAKER)
                        .price(TEST_PRODUCT_PRICE)
                        .imageUrl(TEST_PRODUCT_IMAGE_URL)
                        .build();
        Product product = productService.create(productDto);
        PRODUCT_TEST_ID = product.getId();
    }

    @Test
    @DisplayName("getList 메소드는 모든 상품의 목록을 반환한다.")
    void getList() {
        assertThat(productService.getProducts()).hasSize(1);
    }


    @Nested
    @DisplayName("getProductById 메소드는")
    class Describe_getProductById {

        @Nested
        @DisplayName("아이디가 상품 목록에 존재한다면")
        class Context_with_a_valid_id {

            @Test
            @DisplayName("상품을 찾아서 반환한다.")
            void it_returns_a_product() {
                Product foundProduct = productService.getProductById(PRODUCT_TEST_ID);
                assertThat(foundProduct).isNotNull();
                assertThat(foundProduct.getName()).isEqualTo(TEST_PRODUCT_NAME);
                assertThat(foundProduct.getMaker()).isEqualTo(TEST_PRODUCT_MAKER);
                assertThat(foundProduct.getPrice().intValue()).isEqualTo(BigDecimal.TEN.intValue());
                assertThat(foundProduct.getImageUrl()).isEqualTo(TEST_PRODUCT_IMAGE_URL);
            }
        }

        @Nested
        @DisplayName("아이디가 상품 목록에 존재하지 않는다면")
        class Context_with_a_invalid_id {

            @Test
            @DisplayName("예외를 던진다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> productService.getProductById(NOT_SAVED_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Test
    @DisplayName("create 메소드는 상품을 저장한다.")
    void create() {
        int oldSize = productService.getProducts().size();
        ProductDto resource = new ProductDto.ProductDtoBuilder()
                                .name("New " + TEST_PRODUCT_NAME)
                                .maker(TEST_PRODUCT_MAKER)
                                .price(TEST_PRODUCT_PRICE)
                                .imageUrl(TEST_PRODUCT_IMAGE_URL)
                                .build();

        Product newProduct = productService.create(resource);

        assertThat(productService.getProductById(newProduct.getId()).getName()).isEqualTo("New " + TEST_PRODUCT_NAME);
        assertThat(productService.getProducts().size()).isEqualTo(oldSize + 1);
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
            @Test
            @DisplayName("상품을 수정하여 반환한다.")
            void it_returns_a_updated_product() {
                Product product = productService.updateProduct(PRODUCT_TEST_ID, resource);
                assertThat(product.getName()).isEqualTo(updatedName);
            }
        }

        @Nested
        @DisplayName("아이디가 상품 목록에 존재하지 않는다면")
        class Context_with_a_invalid_id {
            @Test
            @DisplayName("예외를 던진다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> productService.updateProduct(NOT_SAVED_ID, resource))
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
            @Test
            @DisplayName("상품을 삭제한다.")
            void it_returns_a_deleted_product() {
                int oldSize = productService.getProducts().size();
                productService.deleteProduct(PRODUCT_TEST_ID);

                int newSize = productService.getProducts().size();

                assertThatThrownBy(() -> productService.getProductById(PRODUCT_TEST_ID))
                    .isInstanceOf(ProductNotFoundException.class);
                assertThat(newSize).isEqualTo(oldSize - 1);
            }
        }

        @Nested
        @DisplayName("아이디가 상품 목록에 존재하지 않는다면")
        class Context_with_a_invalid_id {
            @Test
            @DisplayName("예외를 던진다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> productService.deleteProduct(NOT_SAVED_ID))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}