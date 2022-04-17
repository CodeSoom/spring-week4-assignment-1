package com.codesoom.assignment.domain;

import com.codesoom.assignment.factories.ProductFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_IMAGE_PATH;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_MAKER;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_NAME;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_PRICE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Product 클래스")
class ProductTest {

    @Nested
    @DisplayName("toString 메소드는")
    class Describe_toString {

        @Nested
        @DisplayName("상품 속성을 포함한")
        class Context_valid {

            final Product product = ProductFactory.createNewProduct(
                    TEST_PRODUCT_NAME, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

            String subject() {
                return product.toString();
            }

            @Test
            @DisplayName("상품 정보를 문자열로 리턴한다.")
            void it_return_string() {
                assertThat(subject()).contains(
                        TEST_PRODUCT_NAME, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE.toString(), TEST_PRODUCT_IMAGE_PATH);
            }
        }
    }
}

