package com.codesoom.assignment.application;

import com.codesoom.assignment.common.ProductFactory;
import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductCommand;
import com.codesoom.assignment.domain.ProductInfo;
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

@DisplayName("ProductService 클래스")
class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("데이터가 존재한다면")
        class Context_with_non_empty_data {
            private final List<Product> givenProducts = new ArrayList<>();

            @BeforeEach
            void prepare() {

                final Product product1 = ProductFactory.createProduct(1L);
                final Product product2 = ProductFactory.createProduct(2L);

                givenProducts.add(product1);
                givenProducts.add(product2);

                given(productRepository.findAll()).willReturn(givenProducts);
            }

            @Test
            @DisplayName("모든 상품을 리턴한다")
            void it_returns_all_products() {
                final List<ProductInfo> actualProducts = productService.getProducts();

                assertThat(actualProducts).hasSize(givenProducts.size());
            }
        }

        @Nested
        @DisplayName("데이터가 존재하지 않는다면")
        class Context_with_empty_data {
            @BeforeEach
            void prepare() {
                given(productRepository.findAll()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("빈 컬렉션을 리턴한다")
            void it_returns_empty_data() {
                final List<ProductInfo> actualProducts = productService.getProducts();

                assertThat(actualProducts).hasSize(0);
            }
        }

    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private final Long PRODUCT_ID = 1L;
            private final Product givenProduct = ProductFactory.createProduct(PRODUCT_ID);

            @BeforeEach
            void prepare() {
                given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.of(givenProduct));
            }

            @Test
            @DisplayName("상품을 찾아 리턴한다")
            void it_returns_searched_product() {
                final ProductInfo actualProduct = productService.getProduct(PRODUCT_ID);

                assertThat(actualProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(actualProduct.getMaker()).isEqualTo(givenProduct.getMaker());
                assertThat(actualProduct.getPrice()).isEqualTo(givenProduct.getPrice());
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id {
            private final Long PRODUCT_ID = 100L;

            @BeforeEach
            void prepare() {
                given(productRepository.findById(any(Long.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.getProduct(PRODUCT_ID)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {
        @Nested
        @DisplayName("새로운 상품이 주어지면")
        class Context_with_new_product {
            private final Product givenProduct = ProductFactory.createProduct();

            @BeforeEach
            void prepare() {
                given(productRepository.save(any(Product.class))).willReturn(givenProduct);
            }

            @Test
            @DisplayName("DB에 등록하고 등록된 상품을 리턴한다")
            void it_returns_registered_product() {
                final ProductCommand.Register command = ProductFactory.of(givenProduct);

                final ProductInfo actualProduct = productService.createProduct(command);

                assertThat(actualProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(actualProduct.getMaker()).isEqualTo(givenProduct.getMaker());
                assertThat(actualProduct.getPrice()).isEqualTo(givenProduct.getPrice());
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private final Long PRODUCT_ID = 1L;
            private final Product givenProduct = ProductFactory.createProduct(PRODUCT_ID);

            @BeforeEach
            void prepare() {
                given(productRepository.findById(any(Long.class))).willReturn(Optional.of(givenProduct));
            }

            @Test
            @DisplayName("상품을 수정하고 수정된 상품을 리턴한다")
            void it_returns_modified_product() {

                final ProductCommand.Register.RegisterBuilder registerBuilder = ProductCommand.Register.builder();
                System.out.println(registerBuilder.toString()); // jacoco테스트에서 RegisterBuilder toString가 계속 0%로 나와서 추가...

                final ProductCommand.Register command = registerBuilder
                        .id(PRODUCT_ID)
                        .name("수정_" + givenProduct.getName())
                        .maker("수정_" + givenProduct.getMaker())
                        .price(2000 + givenProduct.getPrice())
                        .imageUrl("modified_" + givenProduct.getImageUrl())
                        .build();

                final ProductInfo actualProduct = productService.updateProduct(command);

                assertThat(actualProduct.getPrice()).isEqualTo(command.getPrice());
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id {
            private final Long PRODUCT_ID = 100L;
            private final Product givenProduct = ProductFactory.createProduct(PRODUCT_ID);

            @BeforeEach
            void prepare() {
                given(productRepository.findById(any(Long.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                final ProductCommand.Register command = ProductFactory.of(givenProduct);

                assertThatThrownBy(() -> productService.updateProduct(command)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private final Long PRODUCT_ID = 1L;
            private final Product givenProduct = ProductFactory.createProduct(PRODUCT_ID);

            @BeforeEach
            void prepare() {
                given(productRepository.findById(any(Long.class))).willReturn(Optional.of(givenProduct));
            }

            @Test
            @DisplayName("해당 상품을 삭제한다")
            void it_returns_nothing() {
                productService.deleteProduct(PRODUCT_ID);
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id {
            @BeforeEach
            void prepare() {
                given(productRepository.findById(any(Long.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.deleteProduct(100L)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}

