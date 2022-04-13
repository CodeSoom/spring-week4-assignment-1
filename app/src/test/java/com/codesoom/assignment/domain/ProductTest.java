package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    private Product product;

    private static final Long PRODUCT_TEST_ID = 1L;
    private static final String TEST_PRODUCT_NAME = "NAME";
    private static final String TEST_PRODUCT_MAKER = "MAKER";
    private static final BigDecimal TEST_PRODUCT_PRICE = BigDecimal.TEN;
    private static final String TEST_PRODUCT_IMAGE_URL = "/image.png";

    @BeforeEach
    void setUp() {
        product = new Product.ProductBuilder()
                            .id(PRODUCT_TEST_ID)
                            .name(TEST_PRODUCT_NAME)
                            .maker(TEST_PRODUCT_MAKER)
                            .price(TEST_PRODUCT_PRICE)
                            .imageUrl(TEST_PRODUCT_IMAGE_URL)
                            .build();
    }

    @Test
    @DisplayName("getId는 상품의 아이디를 반환한다.")
    void getId() {
        assertThat(product.getId()).isEqualTo(PRODUCT_TEST_ID);
    }

    @Test
    @DisplayName("getName는 상품의 이름을 반환한다.")
    void getName() {
        assertThat(product.getName()).isEqualTo(TEST_PRODUCT_NAME);
    }

    @Test
    @DisplayName("getMaker는 상품의 제작자를 반환한다.")
    void getMaker() {
        assertThat(product.getMaker()).isEqualTo(TEST_PRODUCT_MAKER);
    }

    @Test
    @DisplayName("getPrice는 상품의 가격을 반환한다.")
    void getPrice() {
        assertThat(product.getPrice()).isEqualTo(TEST_PRODUCT_PRICE);
    }

    @Test
    @DisplayName("getImageUrl는 상품의 이미지 주소를 반환한다.")
    void getImageUrl() {
        assertThat(product.getImageUrl()).isEqualTo(TEST_PRODUCT_IMAGE_URL);
    }

    @Test
    @DisplayName("modify 메소드는 ProductDto를 받아 상품의 각 필드를 수정한다.")
    void modify() {

        ProductDto productDto = new ProductDto.ProductDtoBuilder()
                .name("modify name")
                .maker("modify maker")
                .price(BigDecimal.ONE)
                .imageUrl("/dir/test.png")
                .build();

        product.modify(productDto);

        assertThat(product.getName()).isEqualTo(productDto.getName());
        assertThat(product.getMaker()).isEqualTo(productDto.getMaker());
        assertThat(product.getPrice()).isEqualTo(productDto.getPrice());
        assertThat(product.getImageUrl()).isEqualTo(productDto.getImageUrl());
    }

    @Test
    @DisplayName("modify 메소드는 ProductDto를 받아 상품의 각 필드를 수정한다.")
    void modifyWithInvalidValue() {

        ProductDto productDto = new ProductDto.ProductDtoBuilder()
                .build();

        product.modify(productDto);

        assertThat(product.getName()).isEqualTo(product.getName());
        assertThat(product.getMaker()).isEqualTo(product.getMaker());
        assertThat(product.getPrice()).isEqualTo(product.getPrice());
        assertThat(product.getImageUrl()).isEqualTo(product.getImageUrl());
    }

    @Nested
    @DisplayName("modify 메소드는")
    class Describe_modify {

        @Nested
        @DisplayName("ProductDto의 각 필드가 존재하면")
        class Context_with_valid_values {

            @Test
            @DisplayName("상품을 수정한다.")
            void it_returns() {
                ProductDto productDto = new ProductDto.ProductDtoBuilder()
                        .name("modify name")
                        .maker("modify maker")
                        .price(BigDecimal.ONE)
                        .imageUrl("/dir/test.png")
                        .build();

                product.modify(productDto);

                assertThat(product.getName()).isEqualTo(productDto.getName());
                assertThat(product.getMaker()).isEqualTo(productDto.getMaker());
                assertThat(product.getPrice()).isEqualTo(productDto.getPrice());
                assertThat(product.getImageUrl()).isEqualTo(productDto.getImageUrl());
            }
        }

        @Nested
        @DisplayName("ProductDto의 필드가 존재하지 않으면")
        class Context_with_empty_values {

            @Test
            @DisplayName("상품을 수정하지 않는다.")
            void it_returns() {
                ProductDto productDto = new ProductDto();

                product.modify(productDto);

                assertThat(product).isSameAs(product).isEqualTo(product);
            }
        }
    }
}