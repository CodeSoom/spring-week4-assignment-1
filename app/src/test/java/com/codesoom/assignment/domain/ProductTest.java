package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Product Entity")
class ProductTest {

    private final String TEST_PRODUCT_NAME = "고양이장난감";
    private final String TEST_PRODUCT_MAKER = "APPLE";
    private final Integer TEST_PRODUCT_PRICE = 10000;
    private final String TEST_PRODUCT_IMAGE_PATH = "/images/toy.png";

    private final String TEST_PRODUCT_UPDATE_NAME = "고양이장난감-1";
    private final String TEST_PRODUCT_UPDATE_MAKER = "APPLE-1";
    private final Integer TEST_PRODUCT_UPDATE_PRICE = 11000;
    private final String TEST_PRODUCT_UPDATE_IMAGE_PATH = "/images/toy-1.png";

    @Nested
    @DisplayName("replace 메소드는")
    class Describe_replace {

        @Nested
        @DisplayName("주어진 상품 데이터로")
        class Context_givenSource {

            final Product product = new Product(
                    TEST_PRODUCT_NAME, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

            ProductUpdateRequest updateSource;

            @BeforeEach
            void setUp() {
                updateSource = new ProductUpdateRequest() {
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
                    public String getImagePath() {
                        return TEST_PRODUCT_UPDATE_IMAGE_PATH;
                    }
                };
            }

            @Test
            @DisplayName("상품 정보를 변경한다.")
            void it_replace() {

                product.replace(updateSource);

                assertAll(
                        () -> assertThat(product.getName()).isEqualTo(TEST_PRODUCT_UPDATE_NAME),
                        () -> assertThat(product.getMaker()).isEqualTo(TEST_PRODUCT_UPDATE_MAKER),
                        () -> assertThat(product.getPrice()).isEqualTo(TEST_PRODUCT_UPDATE_PRICE),
                        () -> assertThat(product.getImagePath()).isEqualTo(TEST_PRODUCT_UPDATE_IMAGE_PATH)
                );
            }
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("주어진 상품 데이터중")
        class Context_givenSource {

            final Product product = new Product(
                    TEST_PRODUCT_NAME, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

            ProductUpdateRequest updateSource;

            @BeforeEach
            void setUp() {
                updateSource = new ProductUpdateRequest() {
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
                    public String getImagePath() {
                        return "";
                    }
                };
            }

            @Test
            @DisplayName("빈값이 아닌 데이터만 변경한다.")
            void it_update() {

                product.update(updateSource);

                assertAll(
                        () -> assertThat(product.getName()).isEqualTo(TEST_PRODUCT_UPDATE_NAME),
                        () -> assertThat(product.getMaker()).isEqualTo(TEST_PRODUCT_UPDATE_MAKER),
                        () -> assertThat(product.getPrice()).isEqualTo(TEST_PRODUCT_PRICE),
                        () -> assertThat(product.getImagePath()).isEqualTo(TEST_PRODUCT_IMAGE_PATH)
                );
            }
        }
    }
}

