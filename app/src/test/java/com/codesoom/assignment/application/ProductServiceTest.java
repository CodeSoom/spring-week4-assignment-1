package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductServiceTest 클래스의")
public class ProductServiceTest {
    private static final String TEST_NAME = "testName";
    private static final String TEST_MAKER = "testMaker";
    private static final Long TEST_PRICE = 5000L;
    private static final String TEST_IMAGE_PATH = "testImagePath.jpg";
    private static final String CREATE_POSTFIX = "...";
    private static final String UPDATE_POSTFIX = "!!!";

    private static final int PRODUCTS_MAX_SIZE = 5;
    private static final Long VALID_PRODUCT_ID = 1L;
    private static final Long INVALID_PRODUCT_ID = 100L;

    private ProductService productService;

    private ProductRepository productRepository;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);

        productService = new ProductService(productRepository);

        products = new ArrayList<>();

        Product product = null;
        for(int i = 0; i < PRODUCTS_MAX_SIZE; i++) {
            product = new Product(TEST_NAME + (i + 1),
                    TEST_MAKER + (i + 1),
                    TEST_PRICE + (i + 1),
                    (i + 1) + TEST_IMAGE_PATH);
            products.add(product);
        }

        given(productRepository.findAll()).willReturn(products);
        given(productRepository.findById(VALID_PRODUCT_ID))
                .willReturn(Optional.of(products.get(VALID_PRODUCT_ID.intValue() - 1)));
        given(productRepository.findById(INVALID_PRODUCT_ID))
                .willReturn(Optional.empty());
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        @Test
        @DisplayName("Product를 추가하기 전에는 비어있습니다.")
        void getProductsWithEmpty() {
            given(productRepository.findAll()).willReturn(new ArrayList<>());

            List<Product> copy = productService.getProducts();

            verify(productRepository).findAll();

            assertThat(copy).isEmpty();
        }

        @Test
        @DisplayName("5개의 Product를 추가한 후에는 5개의 Product를 가집니다.")
        void getProductsWithProducts() {
            List<Product> copy = productService.getProducts();

            verify(productRepository).findAll();

            assertThat(copy).hasSize(5);

            for(int i = 0; i < PRODUCTS_MAX_SIZE; i++) {
                assertThat(
                        products.get(i).hasEqualContents(copy.get(i))
                ).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {
        @Test
        @DisplayName("Products에 존재하는 Product의 id를 요청하면 해당 Product를 반환합니다.")
        void getProductWithExistedId() {
            Product copy = productService.getProduct(VALID_PRODUCT_ID);

            verify(productRepository).findById(VALID_PRODUCT_ID);

            assertThat(products.get(VALID_PRODUCT_ID.intValue() - 1)
                    .hasEqualContents(copy)).isTrue();
        }

        @Test
        @DisplayName("Products에 존재하지 않는 Product의 id를 요청하면 예외를 발생시킵니다.")
        void getProductWithNotExistedId() {
            assertThatThrownBy(() -> productService.getProduct(INVALID_PRODUCT_ID))
                    .isInstanceOf(ProductNotFoundException.class);

            verify(productRepository).findById(INVALID_PRODUCT_ID);
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {
        private Product newProduct;
        @BeforeEach
        void setUp() {
            newProduct = new Product(
                    TEST_NAME + CREATE_POSTFIX,
                    TEST_MAKER + CREATE_POSTFIX,
                    TEST_PRICE + 1000L,
                    CREATE_POSTFIX + TEST_IMAGE_PATH
            );
            given(productRepository.save(any(Product.class))).willReturn(newProduct);
        }
        @Test
        @DisplayName("Products에 새로운 Product를 추가합니다.")
        void createProduct() {
            Product oldProduct = productService.createProduct(newProduct);

            verify(productRepository).save(any(Product.class));

            assertThat(oldProduct.hasEqualContents(newProduct)).isTrue();
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {
        private Product source;

        @BeforeEach
        void setUp() {
            source = new Product(
                    TEST_NAME + UPDATE_POSTFIX,
                    TEST_MAKER + UPDATE_POSTFIX,
                    TEST_PRICE + 2000L,
                    UPDATE_POSTFIX + TEST_IMAGE_PATH
            );
        }

        @Test
        @DisplayName("유효한 아이디가 있을 때는 해당 id의 Product 정보를 바꿉니다.")
        void updateProductWithExistedId() {
            Product updatedProduct = productService.updateProduct(VALID_PRODUCT_ID, source);

            verify(productRepository).findById(VALID_PRODUCT_ID);

            assertThat(updatedProduct.hasEqualContents(source)).isTrue();
        }

        @Test
        void updateProductWithNotExistedId() {
            assertThatThrownBy(() -> productService.updateProduct(INVALID_PRODUCT_ID, source))
                    .isInstanceOf(ProductNotFoundException.class);

            verify(productRepository).findById(INVALID_PRODUCT_ID);
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {
        @Test
        void deleteProductWithExistedId() {
            productService.deleteProduct(VALID_PRODUCT_ID);

            verify(productRepository).findById(VALID_PRODUCT_ID);

            verify(productRepository).delete(any(Product.class));
        }

        @Test
        void deleteProductWithNotExistedId() {
            assertThatThrownBy(() -> productService.deleteProduct(INVALID_PRODUCT_ID))
                    .isInstanceOf(ProductNotFoundException.class);

            verify(productRepository).findById(INVALID_PRODUCT_ID);
        }
    }
}
