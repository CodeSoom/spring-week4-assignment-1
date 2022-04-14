package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.ProductUpdateRequest;
import com.codesoom.assignment.dto.ProductSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_IMAGE_PATH;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_MAKER;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_NAME;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_PRICE;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_UPDATE_IMAGE_PATH;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_UPDATE_MAKER;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_UPDATE_NAME;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_UPDATE_PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DisplayName("ProductCommandService 클래스")
class ProductCommandServiceTest {

    ProductCommandService productCommandService;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productCommandService = new ProductCommandService(productRepository);
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("saveProduct 메소드는")
    class Describe_saveProduct {

        @Nested
        @DisplayName("상품 등록에 필요한 데이터가 주어진다면")
        class Context_valid {

            final ProductSaveRequest saveRequest = new ProductSaveRequest() {

                @Override
                public String getName() {
                    return TEST_PRODUCT_NAME;
                }

                @Override
                public String getMaker() {
                    return TEST_PRODUCT_MAKER;
                }

                @Override
                public Integer getPrice() {
                    return TEST_PRODUCT_PRICE;
                }

                @Override
                public String getImageUrl() {
                    return TEST_PRODUCT_IMAGE_PATH;
                }
            };

            @Test
            @DisplayName("상품을 등록하고 리턴한다.")
            void it_save_product_and_return() {

                Product product = productCommandService.saveProduct(saveRequest);

                assertAll(
                        () -> assertThat(product.getId()).isNotNull(),
                        () -> assertThat(product.getName()).isEqualTo(TEST_PRODUCT_NAME),
                        () -> assertThat(product.getMaker()).isEqualTo(TEST_PRODUCT_MAKER),
                        () -> assertThat(product.getPrice()).isEqualTo(TEST_PRODUCT_PRICE),
                        () -> assertThat(product.getImagePath()).isEqualTo(TEST_PRODUCT_IMAGE_PATH)
                );
            }
        }
    }

    @Nested
    @DisplayName("replaceProduct 메소드는")
    class Describe_replaceProduct {

        @Nested
        @DisplayName("대체할 상품이 저장소에 있다면")
        class Context_existsProduct {

            final Product product = new Product(
                    TEST_PRODUCT_NAME, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

            final ProductUpdateRequest updateSource = new ProductUpdateRequest() {
                @Override
                public String getName() {
                    return TEST_PRODUCT_UPDATE_NAME;
                }

                @Override
                public String getMaker() {
                    return TEST_PRODUCT_UPDATE_MAKER;
                }

                @Override
                public Integer getPrice() {
                    return TEST_PRODUCT_UPDATE_PRICE;
                }

                @Override
                public String getImageUrl() {
                    return TEST_PRODUCT_UPDATE_IMAGE_PATH;
                }
            };

            Long productId;

            @BeforeEach
            void setUp() {
                productRepository.save(product);
                productId = product.getId();
            }

            @Test
            @DisplayName("상품 정보를 대체하고 리턴한다.")
            void it_replace_and_return_product() {

                final Product replacedProduct = productCommandService.replaceProduct(product, updateSource);

                assertAll(
                        () -> assertThat(replacedProduct.getId()).isEqualTo(productId),
                        () -> assertThat(replacedProduct.getName()).isEqualTo(TEST_PRODUCT_UPDATE_NAME),
                        () -> assertThat(replacedProduct.getMaker()).isEqualTo(TEST_PRODUCT_UPDATE_MAKER),
                        () -> assertThat(replacedProduct.getPrice()).isEqualTo(TEST_PRODUCT_UPDATE_PRICE),
                        () -> assertThat(replacedProduct.getImagePath()).isEqualTo(TEST_PRODUCT_UPDATE_IMAGE_PATH)
                );
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {

        @Nested
        @DisplayName("변경할 상품이 저장소에 있다면")
        class Context_existsProduct {

            final Product product = new Product(
                    TEST_PRODUCT_NAME, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

            final ProductUpdateRequest updateSource = new ProductUpdateRequest() {
                @Override
                public String getName() {
                    return TEST_PRODUCT_UPDATE_NAME;
                }

                @Override
                public String getMaker() {
                    return TEST_PRODUCT_UPDATE_MAKER;
                }

                @Override
                public Integer getPrice() {
                    return null;
                }

                @Override
                public String getImageUrl() {
                    return "";
                }
            };

            Long productId;

            @BeforeEach
            void setUp() {
                productRepository.save(product);
                productId = product.getId();
            }

            @Test
            @DisplayName("상품 정보를 변경하고 리턴한다.")
            void it_update_and_return_product() {

                final Product updatedProduct = productCommandService.updateProduct(product, updateSource);

                assertAll(
                        () -> assertThat(updatedProduct.getId()).isEqualTo(productId),
                        () -> assertThat(updatedProduct.getName()).isEqualTo(TEST_PRODUCT_UPDATE_NAME),
                        () -> assertThat(updatedProduct.getMaker()).isEqualTo(TEST_PRODUCT_UPDATE_MAKER),
                        () -> assertThat(updatedProduct.getPrice()).isEqualTo(TEST_PRODUCT_PRICE),
                        () -> assertThat(updatedProduct.getImagePath()).isEqualTo(TEST_PRODUCT_IMAGE_PATH)
                );
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {

        @Nested
        @DisplayName("삭제할 상품이 저장소에 있다면")
        class Context_valid {

            Product product;

            @BeforeEach
            void setUp() {
                product = new Product();
                productRepository.save(product);
            }

            @Test
            @DisplayName("상품을 삭제한다.")
            void it_delete() {
                productCommandService.deleteProduct(product);

                Optional<Product> foundProduct = productRepository.findById(product.getId());
                assertThat(foundProduct).isEqualTo(Optional.empty());
            }
        }
    }
}
