package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DisplayName("ProductCommandService 클래스")
class ProductCommandServiceTest {

    private static final String TEST_PRODUCT_NAME = "NAME";
    private static final String TEST_PRODUCT_MAKER = "MAKER";
    private static final Integer TEST_PRODUCT_PRICE = 1000;
    private static final String TEST_PRODUCT_IMAGE_PATH = "/image/test.jpg";

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
                public String getImagePath() {
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
