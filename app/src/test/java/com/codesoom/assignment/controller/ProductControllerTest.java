package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductCommandService;
import com.codesoom.assignment.application.ProductQueryService;
import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductController 클래스")
@DataJpaTest
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductController productController;
    private ProductCommandService productCommandService;
    private ProductQueryService productQueryService;

    @Autowired
    private ProductRepository productRepository;

    private Long NOT_SAVED_ID = 100L;
    private static final String TEST_PRODUCT_MAKER = "MAKER";
    private static final String TEST_PRODUCT_NAME = "NAME";
    private static final BigDecimal TEST_PRODUCT_PRICE = BigDecimal.TEN;
    private static final String TEST_PRODUCT_IMAGE_URL = "/image.png";

    @BeforeEach
    void setUp() {
        this.productQueryService = new ProductQueryService(productRepository);
        this.productCommandService = new ProductCommandService(productRepository);
        this.productController = new ProductController(productCommandService, productQueryService);

        // given
        ProductDto resource = new ProductDto.ProductDtoBuilder()
                .name("New " + TEST_PRODUCT_NAME)
                .maker(TEST_PRODUCT_MAKER)
                .price(TEST_PRODUCT_PRICE)
                .imageUrl(TEST_PRODUCT_IMAGE_URL)
                .build();

        productController.createProduct(resource);
    }

    @Test
    @DisplayName("getProductList 메소드는 모든 상품 목록을 반환한다.")
    void getProductList() {
        List<Product> products = productController.getProductList();
        assertThat(products).isNotEmpty();
    }
    
    @Nested
    @DisplayName("getProductById 메소드는")
    class Describe_getProductById {

        @Nested
        @DisplayName("아이디가 상품 목록에 존재한다면")
        class Context_with_a_valid_id {

            private Long productId;

            @BeforeEach
            void prepareProduct() {
                ProductDto productInfo = new ProductDto.ProductDtoBuilder()
                        .name(TEST_PRODUCT_NAME)
                        .maker(TEST_PRODUCT_MAKER)
                        .price(TEST_PRODUCT_PRICE)
                        .imageUrl(TEST_PRODUCT_IMAGE_URL)
                        .build();
                productId = productController.createProduct(productInfo).getId();
            }

            @Test
            @DisplayName("상품을 찾아서 반환한다.")
            void it_returns_a_product() {
                Product product = productController.getProductById(productId);
                assertThat(product).isNotNull();
                assertThat(product.getName()).isEqualTo(TEST_PRODUCT_NAME);
                assertThat(product.getMaker()).isEqualTo(TEST_PRODUCT_MAKER);
                assertThat(product.getImageUrl()).isEqualTo(TEST_PRODUCT_IMAGE_URL);
            }
        }

        @Nested
        @DisplayName("아이디가 상품 목록에 존재하지 않는다면")
        class Context_with_a_invalid_id {

            @Test
            @DisplayName("예외를 던진다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> productController.getProductById(NOT_SAVED_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Test
    @DisplayName("createProduct 메소드는 새로운 상품을 저장하여 반환한다.")
    void createProduct() {
        // given
        String newName = "New " + TEST_PRODUCT_NAME;
        int oldSize = productController.getProductList().size();
        ProductDto resource = new ProductDto.ProductDtoBuilder()
                .name(newName)
                .maker(TEST_PRODUCT_MAKER)
                .price(TEST_PRODUCT_PRICE)
                .imageUrl(TEST_PRODUCT_IMAGE_URL)
                .build();


        // when
        Product newProduct = productController.createProduct(resource);

        // then
        Product foundProduct = productController.getProductById(newProduct.getId());
        int newSize = productController.getProductList().size();

        assertThat(foundProduct.getName()).isEqualTo(newName);
        assertThat(newSize).isEqualTo(oldSize + 1);
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {

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
                Product product = productController.createProduct(new ProductDto());
                productId = product.getId();
            }

            @Test
            @DisplayName("상품을 수정하여 반환한다.")
            void it_returns_a_updated_product() {
                Product product = productController.updateProduct(productId, resource);
                assertThat(product.getName()).isEqualTo(updatedName);
            }
        }

        @Nested
        @DisplayName("아이디가 상품 목록에 존재하지 않는다면")
        class Context_with_a_invalid_id {
            @Test
            @DisplayName("예외를 던진다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> productController.updateProduct(NOT_SAVED_ID, resource))
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

            private int oldSize = 0;
            private Long productId;

            @BeforeEach
            void prepareProduct() {
                Product product = productCommandService.create(new ProductDto());
                productId = product.getId();
                oldSize = productController.getProductList().size();
            }

            @Test
            @DisplayName("상품을 삭제한다.")
            void it_returns_a_deleted_product() {
                productController.deleteProduct(productId);

                int newSize = productController.getProductList().size();

                assertThatThrownBy(() -> productController.getProductById(productId))
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
                assertThatThrownBy(() -> productController.deleteProduct(NOT_SAVED_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}