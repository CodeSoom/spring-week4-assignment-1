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
    private Product TEST_PRODUCT;
    private Product UPDATE_PRODUCT;
    private Long VALID_ID = 1L;
    private Long INVALID_ID = 100L;

    @BeforeEach
    void setUp() {

        TEST_PRODUCT = new Product(1L,"name1","maker1",1000L,"img1");
        UPDATE_PRODUCT = new Product(null, "update_name1", "update_maker1", 1500L, "update_img1");

        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);

    }

    @Nested
    @DisplayName("register 메소드는")
    class Describe_register {

        @Nested
        @DisplayName("영구 저장소에 등록할 product가 있다면")
        class Context_exist_product {

            @BeforeEach
            void setUp() {

                given(productService.register(any(Product.class))).willReturn(TEST_PRODUCT);

            }

            @Test
            @DisplayName("영구 저장소에 저장 후 product를 반환합니다")
            void It_save_product() {

                Product createProduct = productService.register(TEST_PRODUCT);

                assertThat(createProduct.getName()).isEqualTo(TEST_PRODUCT.getName());

                verify(productRepository).save(TEST_PRODUCT);

            }

        }

    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {

        @BeforeEach
        void setUp() {

            // 아이디가 존재할 때
            given(productRepository.findById(VALID_ID)).willReturn(Optional.of(TEST_PRODUCT));

            // 아이디가 존재하지 않을 때
            given(productRepository.findById(INVALID_ID)).willThrow(NoSuchElementException.class);

        }

        @Nested
        @DisplayName("영구 저장소에 product 상품이 존재하면")
        class Context_exist_id {

            @Test
            @DisplayName("id를 통해 product 객체를 리턴한다")
            void It_return_product() {

                Product foundProduct = productService.getProduct(VALID_ID);

                assertThat(foundProduct.getId()).isEqualTo(VALID_ID);

                verify(productRepository).findById(VALID_ID);

            }

        }

        @Nested
        @DisplayName("영구 저장소에 product 상품이 존재하지 않다면")
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
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {

        @Nested
        @DisplayName("디비에 장난감들이 존재하면")
        class Context_exist_products {

            List<Product> products;

            @BeforeEach
            void setUp() {

                products = new ArrayList<>();
                products.add(new Product(1L,"name1","maker1",1000L,"img1"));
                products.add(new Product(2L,"name2","maker2",2000L,"img2"));
                products.add(new Product(3L,"name3","maker3",3000L,"img3"));

                given(productService.getProducts()).willReturn(products);
            }

            @Test
            @DisplayName("장난감 들을 리턴한다")
            void It_return_products() {

                List<Product> foundProducts = productService.getProducts();

                assertThat(foundProducts).hasSize(3);

                verify(productRepository).findAll();

            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {

        @Nested
        @DisplayName("영구 저장소에 수정 할 product가 있다면")
        class Context_exist_product {

            @BeforeEach
            void setUp() {

                given(productRepository.findById(VALID_ID)).willReturn(Optional.of(TEST_PRODUCT));

                given(productRepository.findById(INVALID_ID)).willThrow(NoSuchElementException.class);
            }

            @Test
            @DisplayName("아이디와 product 수정 객체를 받아 수정한다")
            void It_update_product() {

                Product updateProduct = productService.updateProduct(VALID_ID, UPDATE_PRODUCT);

                verify(productRepository).findById(VALID_ID);

                assertThat(updateProduct.getName()).isEqualTo(UPDATE_PRODUCT.getName());

            }

            @Test
            @DisplayName("아이디가 없다면 NoSuchElementsException 을 던진다")
            void It_not_update_product() {

                Assertions.assertThatThrownBy(() -> productRepository.findById(INVALID_ID))
                        .isInstanceOf(NoSuchElementException.class);

            }
        }

    }

}
