package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@DisplayName("ProductServiceTest 클래스")
class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductService productService;

    private List<Product> products;
    private Product TEST_PRODUCT;
    private Product UPDATE_PRODUCT;

    private Long VALID_ID = 1L;
    private Long INVALID_ID = 100L;

    @BeforeEach
    void setUp() {

        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);

        // getProducts 테스트 setUp
        products = new ArrayList<>();
        products.add(new Product(1L,"name1","maker1",1000L,"img1"));
        products.add(new Product(2L,"name2","maker2",2000L,"img2"));
        products.add(new Product(3L,"name3","maker3",3000L,"img3"));

        // register, update, getProduct 테스트 setUp
        TEST_PRODUCT = new Product(1L,"name1","maker1",1000L,"img1");
        UPDATE_PRODUCT = new Product(null, "update_name1", "update_maker1", 1500L, "update_img1");

    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {

        @Nested
        @DisplayName("제품 저장소에 제품들이 존재하면")
        class Context_exist_products {

            @BeforeEach
            void setUp() {

                given(productService.getProducts()).willReturn(products);

            }

            @Test
            @DisplayName("제품 저장소에 있는 제품들을 리턴한다")
            void It_return_products() {

                List<Product> product = productService.getProducts();

                assertThat(product).hasSize(3);

                verify(productRepository).findAll();

            }

        }

    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {

        @BeforeEach
        void setUp() {

            ID_CHECK_SETUP();

        }

        @Nested
        @DisplayName("제품 저장소에 검색한 제품이 존재하면")
        class Context_exist_id {

            @Test
            @DisplayName("제품 객체를 리턴한다")
            void It_return_product() {

                Product product = productService.getProduct(VALID_ID);

                assertThat(product.getId()).isEqualTo(VALID_ID);

                verify(productRepository).findById(VALID_ID);

            }

        }

        @Nested
        @DisplayName("제품 저장소에 검색한 제품을 찾을 수 없다면")
        class Context_exist_not_id {

            @Test
            @DisplayName("NoSuchElementException 을 던진다")
            void It_return_product() {

                Assertions.assertThatThrownBy(() -> productService.getProduct(INVALID_ID))
                        .isInstanceOf(NoSuchElementException.class);

            }

        }

    }

    @Nested
    @DisplayName("register 메소드는")
    class Describe_register {

        @Nested
        @DisplayName("제품 저장소에 등록할 제품이 있다면")
        class Context_exist_product {

            @BeforeEach
            void setUp() {

                given(productService.register(any(Product.class))).willReturn(TEST_PRODUCT);

            }

            @Test
            @DisplayName("제품 저장소에 저장 후 등록한 제품을 반환합니다")
            void It_save_product() {

                Product product = productService.register(TEST_PRODUCT);

                assertThat(product.getName()).isEqualTo(TEST_PRODUCT.getName());

                verify(productRepository).save(TEST_PRODUCT);

            }

        }

    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {

        @BeforeEach
        void setUp() {

            ID_CHECK_SETUP();

        }

        @Nested
        @DisplayName("제품 저장소에 수정 할 제품이 있다면")
        class Context_exist_product {

            @Test
            @DisplayName("제품을 수정하고, 수정한 제품을 리턴한다")
            void It_update_product() {

                Product product = productService.updateProduct(VALID_ID, UPDATE_PRODUCT);

                verify(productRepository).findById(VALID_ID);

                assertThat(product.getName()).isEqualTo(UPDATE_PRODUCT.getName());

            }

        }

        @Nested
        @DisplayName("제품 저장소에 제품을 찾을 수 없다면")
        class Context_exist_not_product {

            @Test
            @DisplayName("NoSuchElementsException 을 던진다")
            void It_not_update_product() {

                Assertions.assertThatThrownBy(() -> productRepository.findById(INVALID_ID))
                        .isInstanceOf(NoSuchElementException.class);

            }

        }

    }

    private void ID_CHECK_SETUP() {

        // 아이디가 존재할 때
        given(productRepository.findById(VALID_ID)).willReturn(Optional.of(TEST_PRODUCT));

        // 아이디가 존재하지 않을 때
        given(productRepository.findById(INVALID_ID)).willThrow(NoSuchElementException.class);

    }

}
