package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("ProductsService 클래스는")
public class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository, new ModelMapper());
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("등록된 Product가 있다면")
        class Context_has_product {
            final int givenProductCnt = 5;

            @BeforeEach
            void prepare() {
                List<Product> products = new ArrayList<>();
                IntStream.range(0, givenProductCnt).forEach((i) -> products.add(getProduct()));

                given(productRepository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("Product의 전체 리스트를 리턴한다.")
            void it_return_products() {
                assertThat(productService.getProducts()).hasSize(givenProductCnt);
            }
        }

        @Nested
        @DisplayName("등록된 Product가 없다면")
        class Context_has_not_product {

            @BeforeEach
            void prepare() {
                List<Product> products = new ArrayList<>();

                given(productRepository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("빈 리스트를 리턴한다.")
            void it_return_products() {
                assertThat(productService.getProducts()).isEmpty();
            }
        }

        @Nested
        @DisplayName("등록된 Product의 id 값이 주어진다면")
        class Context_with_id {
            private Long givenProductId = 1L;

            @BeforeEach
            void prepare() {
                given(productRepository.findById(givenProductId)).willReturn(Optional.of(getProduct()));
            }

            @Test
            @DisplayName("등록된 product 정보를 리턴한다.")
            void it_return_product() {
                Product foundProduct = productService.getProduct(givenProductId);

                assertThat(foundProduct).isNotNull();
            }
        }

        @Nested
        @DisplayName("등록되지 않은 Product의 id 값이 주어진다면")
        class Context_with_invalid_id {
            private Long givenInvalidId = 100L;

            @BeforeEach
            void prepare() {
                given(productRepository.findById(givenInvalidId)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("Product를 찾을 수 없다는 내용의 예외를 던진다.")
            void it_return_productNotFoundException() {
                assertThatThrownBy(() -> productService.getProduct(givenInvalidId)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {
        @Nested
        @DisplayName("등록할 Product가 주어진다면")
        class Context_with_product {
            private Product givenProduct = getProduct();
            private Long givenProductId = 1L;

            @BeforeEach
            void prepare() {
                given(productRepository.save(any(Product.class))).will(invocation -> {
                    Product product =  invocation.getArgument(0);
                    product.setId(givenProductId);
                    return product;
                });
            }

            @Test
            @DisplayName("Product를 생성하고, 리턴한다.")
            void it_create_product_return_product() {
                Product createdProduct = productService.createProduct(givenProduct);

                verify(productRepository).save(any(Product.class));

                assertThat(createdProduct.getId()).isEqualTo(givenProductId);
                assertThat(createdProduct.getName()).isEqualTo(givenProduct.getName());
            }
        }

        @Nested
        @DisplayName("null 주어진다면")
        class Context_without_product {
            private Product givenNullProduct = null;

            @BeforeEach
            void prepare() {
                given(productRepository.save(null)).willThrow(NullPointerException.class);
            }

            @Test
            @DisplayName("NullPointerException을 던진다.")
            void it_return_exception() {
                assertThatThrownBy(() -> productService.createProduct(givenNullProduct)).isInstanceOf(NullPointerException.class);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {
        @Nested
        @DisplayName("등록된 Product의 id와 수정할 Product가 주어진다면")
        class Context_with_id_and_product {
            private Long givenProductId = 1L;
            private Product givenSource = getProductToBeUpdated();

            @BeforeEach
            void prepare() {
                given(productRepository.findById(givenProductId)).willReturn(Optional.of(getProduct()));
                given(productRepository.save(any(Product.class))).will(invocation -> {
                    Product product =  invocation.getArgument(0);
                    product.setId(givenProductId);
                    return product;
                });
            }

            @Test
            @DisplayName("해당 id의 Product를 수정하고, 리턴한다.")
            void it_update_product_return_product() {
                Product updatedProduct = productService.updateProduct(givenProductId, givenSource);

                verify(productRepository).findById(givenProductId);

                assertThat(updatedProduct.getName()).isEqualTo(givenSource.getName());
            }
        }

        @Nested
        @DisplayName("등록된 Product의 id과 null 주어진다면")
        class Context_with_id {
            private Long givenProductId = 1L;
            private Product givenNullProduct = null;

            @BeforeEach
            void prepare() {
                given(productRepository.findById(givenProductId)).willReturn(Optional.of(getProduct()));
            }

            @Test
            @DisplayName("IllegalArgumentException 던진다.")
            void it_update_product_return_illegalArgumentException() {
                assertThatThrownBy(() -> productService.updateProduct(givenProductId, givenNullProduct)).isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("등록된 Product Id가 null로 주어진다면")
        class Context_with_product {
            private Long givenNullId = null;

            @BeforeEach
            void prepare() {
                given(productRepository.findById(givenNullId)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("Product를 찾을 수 없다는 내용의 예외를 던진다.")
            void it_return_productNotFoundException() {
                assertThatThrownBy(() -> productService.updateProduct(givenNullId, getProductToBeUpdated())).isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 Product의 id 와 Product가 있다면 ")
        class Context_with_invalid_id_and_product {
            private Long givenInvalidId = 100L;

            @BeforeEach
            void prepare() {
                given(productRepository.findById(givenInvalidId)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("Product를 찾을 수 없다는 내용의 예외를 던진다.")
            void it_return_productNotFoundException() {
                assertThatThrownBy(() -> productService.updateProduct(givenInvalidId, getProduct())).isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("deleteProduct 메소드")
        class Describe_deleteProduct {
            @Nested
            @DisplayName("등록된 Product의 id가 주어진다면")
            class Context_with_id {
                private Long givenProductId = 1L;

                @BeforeEach
                void prepare() {
                    given(productRepository.findById(givenProductId)).willReturn(Optional.of(getProduct()));
                }

                @Test
                @DisplayName("등록된 Product를 삭제하고, 빈값이 리턴한다.")
                void it_delete_product_return() {
                    productService.deleteProduct(givenProductId);

                    verify(productRepository).findById(givenProductId);
                    verify(productRepository).delete(any(Product.class));
                }
            }

            @Nested
            @DisplayName("등록되지 않은 Product의 id가 주어진다면")
            class Context_with_invalid_id {
                private Long givenProductInvalidId = 100L;

                @Test
                @DisplayName("Product를 찾을 수 없다는 내용의 예외를 던진다.")
                void it_return_productNotFoundException() {
                    assertThatThrownBy(() -> productService.deleteProduct(givenProductInvalidId)).isInstanceOf(ProductNotFoundException.class);

                    verify(productRepository).findById(givenProductInvalidId);
                }
            }
        }
    }
    
    private Product getProduct() {
        return Product.builder()
                .name("테스트 제품")
                .maker("테스트 메이커")
                .price(1000)
                .image("http://test.com/test.jpg")
                .build();
    }

    private Product getProductToBeUpdated() {
        return Product.builder()
                .name("업데이트 제품")
                .maker("업데이트 메이커")
                .price(2000)
                .image("http://update.com/update.jpg")
                .build();
    }
}
