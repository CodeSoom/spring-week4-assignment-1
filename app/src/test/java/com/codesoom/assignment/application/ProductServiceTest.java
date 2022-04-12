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

@DisplayName("ProductServiceTest 클래스")
public class ProductServiceTest {
    private static final String TEST_NAME = "testName";
    private static final String TEST_MAKER = "testMaker";
    private static final Long TEST_PRICE = 5000L;
    private static final String TEST_IMAGE_PATH = "testImagePath.jpg";
    private static final String CREATE_POSTFIX = "...";
    private static final String UPDATE_POSTFIX = "!!!";

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);

        productService = new ProductService(productRepository);

        setUpFixture();
        setUpSaveCatToy();
    }

    void setUpFixture() {
        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setName(TEST_NAME);
        product.setMaker(TEST_MAKER);
        product.setPrice(TEST_PRICE);
        product.setImagePath(TEST_IMAGE_PATH);

        products.add(product);

        given(productRepository.findAll()).willReturn(products);

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(productRepository.findById(100L)).willReturn(Optional.empty());
    }

    void setUpSaveCatToy() {
        given(productRepository.save(any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(2L);
            return product;
        });
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("Product를 추가하기 전에는")
        class Context_before_adding_product {
            @BeforeEach
            void setUp() {
                given(productRepository.findAll()).willReturn(new ArrayList<>());
            }
            @Test
            @DisplayName("비어있습니다.")
            void getProductsWithEmpty() {
                List<Product> products = productService.getProducts();

                verify(productRepository).findAll();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("Product를 5개 추가한 후에는")
        class Context_after_adding_product {
            final static int PRODUCTS_SIZE = 5;
            @BeforeEach
            void setUp() {
                List<Product> products = new ArrayList<>();

                Product product;
                for(int i = 0; i < PRODUCTS_SIZE; i++) {
                    product = new Product();
                    product.setName(TEST_NAME + (i + 1));
                    product.setMaker(TEST_MAKER + (i + 1));
                    product.setPrice(TEST_PRICE + (i + 1));
                    product.setImagePath((i + 1) + TEST_IMAGE_PATH);
                    products.add(product);
                }

                given(productRepository.findAll()).willReturn(products);
            }
            @Test
            @DisplayName("Product를 5개 가지고 있습니다.")
            void getProductsWithProducts() {
                List<Product> products = productService.getProducts();

                verify(productRepository).findAll();

                assertThat(products).hasSize(5);

                Product product;
                for(int i = 0; i < PRODUCTS_SIZE; i++) {
                    product = products.get(i);
                    assertThat(product.getName()).isEqualTo(TEST_NAME + (i + 1));
                }
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는 Products에 Product가 1개 있을 때")
    class Describe_getProduct {
        final Long VALID_REQUEST_ID = 1L;
        final Long INVALID_REQUEST_TASK_ID = 100L;
        @BeforeEach
        void setUp() {
            List<Product> products = new ArrayList<>();

            Product product = new Product();
            product.setName(TEST_NAME);
            product.setMaker(TEST_MAKER);
            product.setPrice(TEST_PRICE);
            product.setImagePath(TEST_IMAGE_PATH);

            products.add(product);

            given(productRepository.findById(VALID_REQUEST_ID))
                    .willReturn(Optional.of(product));
            given(productRepository.findById(INVALID_REQUEST_TASK_ID))
                    .willReturn(Optional.empty());
        }
        @Test
        @DisplayName("id(1)에 해당하는 Product를 요청하면 Product를 반환합니다.")
        void getProductWithExistedId() {
            Product product = productService.getProduct(VALID_REQUEST_ID);

            verify(productRepository).findById(VALID_REQUEST_ID);

            assertThat(product.getName()).isEqualTo(TEST_NAME);
        }

        @Test
        @DisplayName("id(100)에 해당하는 Product를 요청하면 TaskNotFoundException 예외를 던집니다.")
        void getProductWithNotExistedId() {
            assertThatThrownBy(() -> productService.getProduct(100L))
                    .isInstanceOf(ProductNotFoundException.class);

            verify(productRepository).findById(100L);
        }
    }

    @Test
    void createCatToy() {
        Product source = new Product();
        source.setName(TEST_NAME + CREATE_POSTFIX);
        source.setMaker(TEST_MAKER + CREATE_POSTFIX);
        source.setPrice(TEST_PRICE + 1000L);
        source.setImagePath(CREATE_POSTFIX + TEST_IMAGE_PATH);

        Product product = productService.createProduct(source);

        verify(productRepository).save(any(Product.class));

        assertThat(product.getId()).isEqualTo(2L);
        assertThat(product.getName()).isEqualTo(TEST_NAME + CREATE_POSTFIX);
    }

    @Test
    void updateCatToyWithExistedId() {
        Product source = new Product();
        source.setName(TEST_NAME + UPDATE_POSTFIX);

        Product product = productService.updateProduct(1L, source);

        verify(productRepository).findById(1L);

        assertThat(product.getName()).isEqualTo(TEST_NAME + UPDATE_POSTFIX);
    }

    @Test
    void updateCatToyWithNotExistedId() {
        Product source = new Product();
        source.setName(TEST_NAME + UPDATE_POSTFIX);

        assertThatThrownBy(() -> productService.updateProduct(100L, source))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(100L);
    }

    @Test
    void deleteCatToyWithExistedId() {
        productService.deleteProduct(1L);

        verify(productRepository).findById(1L);

        verify(productRepository).delete(any(Product.class));
    }

    @Test
    void deleteCatToyWithNotExistedId() {
        assertThatThrownBy(() -> productService.deleteProduct(100L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(100L);
    }
}
