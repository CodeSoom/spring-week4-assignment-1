package com.codesoom.assignment.product.application;

import com.codesoom.assignment.ProvideInvalidProductArguments;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import com.codesoom.assignment.product.exception.ProductInvalidFieldException;
import com.codesoom.assignment.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import static com.codesoom.assignment.Constant.IMAGE_URL;
import static com.codesoom.assignment.Constant.MAKER;
import static com.codesoom.assignment.Constant.NAME;
import static com.codesoom.assignment.Constant.PRICE;
import static com.codesoom.assignment.Constant.OTHER_IMAGE_URL;
import static com.codesoom.assignment.Constant.OTHER_MAKER;
import static com.codesoom.assignment.Constant.OTHER_NAME;
import static com.codesoom.assignment.Constant.OTHER_PRICE;

@DisplayName("ProductService 서비스 테스트")
public class ProductServiceNestedTest {

    private ProductService service;
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(ProductRepository.class);

        service = new ProductService(repository);

    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Nested
        @DisplayName("저장된 상품들이 없을 경우")
        class Context_without_product {

            @BeforeEach
            void prepareSetUp() {
                given(repository.findAll()).willReturn(new ArrayList<>());
            }

            @DisplayName("빈 목록이 반환된다.")
            @Test
            void findAllNotExistsProduct() {
                final List<Product> products = service.findAll();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 상품이 있을 경우")
        class Context_with_product {
            final Product givenProduct = Product.of(NAME, MAKER, PRICE, IMAGE_URL);

            @BeforeEach
            void prepareSaveProducts() {
                given(repository.findAll()).willReturn(Collections.singletonList(givenProduct));
            }

            @DisplayName("목록이 반환된다.")
            @Test
            void findAllExistsProduct() {
                assertThat(service.findAll()).isNotEmpty();
            }
        }

    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        @Nested
        @DisplayName("조회하고자 하는 식별자가 존재할 경우 ")
        class Context_with_primary_key {
            private final Product givenProduct = Product.of(NAME, MAKER, PRICE, IMAGE_URL);

            @BeforeEach
            void prepareSetUp() {
                ReflectionTestUtils.setField(givenProduct, "id", 1L);

                given(repository.findById(givenProduct.getId())).willReturn(Optional.of(givenProduct));
            }

            @DisplayName("상품 상세정보를 반환한다.")
            @Test
            void findByExistsId() {
                final Product foundProduct = service.findById(givenProduct.getId());

                assertThat(foundProduct.getId()).isEqualTo(givenProduct.getId());
                assertThat(foundProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(foundProduct.getMaker()).isEqualTo(givenProduct.getMaker());
                assertThat(foundProduct.getPrice()).isEqualTo(givenProduct.getPrice());
                assertThat(foundProduct.getImageUrl()).isEqualTo(givenProduct.getImageUrl());

                assertThat(foundProduct).isEqualTo(givenProduct);
            }
        }

        @Nested
        @DisplayName("조회하고자 하는 식별자가 존재하지 않을 경우")
        class Context_without_primary_key {

            @BeforeEach
            void prepareSetUp() {
                given(repository.findById(100L))
                        .willThrow(new ProductNotFoundException(100L));
            }

            @DisplayName("예외가 발생한다.")
            @Test
            void findByNotExistsId() {
                assertThatThrownBy(() -> service.findById(100L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {

        @Nested
        @DisplayName("저장하고자 하는 상품 정보가")
        class Context_with_data {
            Product givenProduct;

            @Nested
            @DisplayName("모두 유효한 경우")
            class Context_with_valid_data {

                @BeforeEach
                void prepareSetUp() {
                    givenProduct = Product.of(NAME, MAKER, PRICE, IMAGE_URL);
                    ReflectionTestUtils.setField(givenProduct, "id", 1L);

                    given(repository.save(any(Product.class)))
                            .willReturn(givenProduct);
                }


                @DisplayName("저장된 후 저장된 결과를 반환한다.")
                @Test
                void createProduct() {
                    final Product product = Product.of(NAME, MAKER, PRICE, IMAGE_URL);
                    final Product savedProduct = service.save(product);

                    assertThat(savedProduct.getId()).isNotNull();
                    assertThat(savedProduct.getName()).isEqualTo(product.getName());
                    assertThat(savedProduct.getMaker()).isEqualTo(product.getMaker());
                    assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
                    assertThat(savedProduct.getImageUrl()).isEqualTo(product.getImageUrl());
                }
            }

            @Nested
            @DisplayName("유효하지 않은 정보가 있을 경우")
            class Context_with_invalid_data {

                @BeforeEach
                void prepareSetUp() {
                    given(repository.save(any(Product.class)))
                            .willThrow(new ProductInvalidFieldException());
                }

                @DisplayName("예외가 발생한다.")
                @ParameterizedTest
                @ArgumentsSource(ProvideInvalidProductArguments.class)
                void createProductWithInvalidData(List<Product> productList) {
                    for (Product product : productList) {
                        assertThatThrownBy(() -> service.save(product))
                                .isInstanceOf(ProductInvalidFieldException.class);
                    }
                }
            }
        }
    }


    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_updateProduct {

        @Nested
        @DisplayName("변경할 식별자가 존재 할 경우")
        class Context_with_existsId {
            final Product originProduct = Product.of(NAME, MAKER, PRICE, IMAGE_URL);

            @BeforeEach
            void prepareSetUp() {
                ReflectionTestUtils.setField(originProduct, "id", 1L);

                given(repository.findById(originProduct.getId())).willReturn(Optional.of(originProduct));
            }

            @Nested
            @DisplayName("변경할 상품 정보가 유효하면")
            class Context_with_valid_data {
                final Product otherProduct = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);


                @BeforeEach
                void prepareSetUp() {
                    ReflectionTestUtils.setField(otherProduct, "id", 1L);
                }


                @DisplayName("정상적으로 수정되고 수정된 정보를 반환한다.")
                @Test
                void updateProduct() {
                    final Product updatedProduct = service.updateProduct(originProduct.getId(), otherProduct);

                    assertThat(updatedProduct.getId()).isEqualTo(originProduct.getId());
                    assertThat(updatedProduct.getName()).isEqualTo(OTHER_NAME);
                    assertThat(updatedProduct.getMaker()).isEqualTo(OTHER_MAKER);
                    assertThat(updatedProduct.getPrice()).isEqualTo(OTHER_PRICE);
                    assertThat(updatedProduct.getImageUrl()).isEqualTo(OTHER_IMAGE_URL);
                }
            }

            @Nested
            @DisplayName("변경할 상품 정보가 유효하지 않으면")
            class Context_with_invalid_data {

                @DisplayName("예외가 발생합니다.")
                @ParameterizedTest
                @ArgumentsSource(ProvideInvalidProductArguments.class)
                void updateProductWithInvalidData(List<Product> products) {
                    for (Product product : products) {
                        assertThatThrownBy(() -> service.updateProduct(originProduct.getId(), product))
                                .isInstanceOf(ProductInvalidFieldException.class);
                    }
                }

            }
        }

        @Nested
        @DisplayName("변경할 식별자가 존재하지 않을 경우")
        class Context_with_not_existsId {
            @BeforeEach
            void prepareSetUp() {
                given(repository.findById(100L)).willThrow(new ProductNotFoundException(100L));
            }

            @DisplayName("예외가 발생합니다.")
            @Test
            void updateProductNotExistsId() {
                assertThatThrownBy(() -> service.updateProduct(100L, Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL)))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("삭제하려는 상품의 식별자가 존재할 경우")
        class Context_with_existsId {
            final Product givenProduct = Product.of(NAME, MAKER, PRICE, IMAGE_URL);

            @DisplayName("정상적으로 상품 정보가 삭제된다.")
            @Test
            void deleteProduct() {
                service.deleteProduct(givenProduct);

                verify(repository).delete(givenProduct);
            }
        }
    }


}
