package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductSaveDto;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("ProductController 클래스")
public class ProductControllerTest {

    private static final String TEST_PRODUCT_MAKER = "MAKER";
    private static final Integer TEST_PRODUCT_PRICE = 1000;
    private static final String TEST_PRODUCT_IMAGE_PATH = "/image/test.jpg";

    @Autowired
    ProductController productController;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("list 메소드는")
    @Transactional
    class Describe_list {

        @Nested
        @DisplayName("주어진 상품 수 만큼")
        class Context_givenCount {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {
                LongStream.rangeClosed(1, givenCount)
                        .mapToObj(index -> new Product())
                        .forEach(product -> productRepository.save(product));
            }

            @Test
            @DisplayName("상품 목록을 리턴한다.")
            void it_return_toys() {
                List<Product> products = productController.list();

                assertThat(products).hasSize(givenCount);
            }
        }
    }

    @Nested
    @DisplayName("detail 메소드는")
    class Describe_detail {

        Long productId;

        @Nested
        @DisplayName("주어진 아이디와 일치하는 상품이 있다면")
        class Context_existsProduct {

            @BeforeEach
            void setUp() {
                Product product = new Product(TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);
                productRepository.save(product);
                productId = product.getId();
            }

            @Test
            @DisplayName("상품 데이터를 리턴한다.")
            void it_return_product() {

                Product product = productController.detail(productId);

                assertThat(product.getId()).isEqualTo(productId);
            }
        }

        @Nested
        @DisplayName("주어진 아이디와 일치하는 상품이 없다면")
        class Context_notExistsProduct {

            final Long notExistsProductId = 999L;

            @Test
            @DisplayName("예외를 던진다.")
            void it_throw_exception() {
                assertThatThrownBy(() -> productController.detail(notExistsProductId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {

        @Nested
        @DisplayName("상품 등록에 필요한 데이터가 주어진다면")
        class Context_valid {

            final ProductSaveDto source = new ProductSaveDto(TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

            @Test
            @DisplayName("상품을 생성하고 리턴한다.")
            void it_save_and_return_product() {

                Product product = productController.save(source);

                assertAll(
                        () -> assertThat(product.getId()).isNotNull(),
                        () -> assertThat(product.getMaker()).isEqualTo(TEST_PRODUCT_MAKER),
                        () -> assertThat(product.getPrice()).isEqualTo(TEST_PRODUCT_PRICE),
                        () -> assertThat(product.getImagePath()).isEqualTo(TEST_PRODUCT_IMAGE_PATH)
                );
            }
        }
    }
}
