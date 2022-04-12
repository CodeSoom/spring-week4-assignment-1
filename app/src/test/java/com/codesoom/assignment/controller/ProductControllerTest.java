package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductCommandService;
import com.codesoom.assignment.application.ProductQueryService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductSaveDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductController 클래스")
public class ProductControllerTest {

    private static final String TEST_MAKER = "MAKER";
    private static final Integer TEST_PRICE = 1000;
    private static final String TEST_IMAGE_PATH = "/image/test.jpg";

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductCommandService productCommandService;

    @Mock
    private ProductQueryService productQueryService;

    @BeforeEach
    void setUp() {
    }

    @Nested
    @DisplayName("list 메소드는")
    class Describe_list {

        @Nested
        @DisplayName("주어진 상품 수 만큼")
        class Context_givenCount {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {
                List<Product> products = LongStream.rangeClosed(1, givenCount)
                        .mapToObj(Product::new)
                        .collect(Collectors.toList());

                given(productQueryService.getProducts()).willReturn(products);
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

        final Long productId = 1L;

        @Nested
        @DisplayName("주어진 아이디와 일치하는 상품이 있다면")
        class Context_existsProduct {

            @Test
            @DisplayName("상품 데이터를 리턴한다.")
            void it_return_product() {

                Product product = productController.detail(productId);

                assertThat(product.getId()).isEqualTo(productId);
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {

        @Nested
        @DisplayName("상품 등록에 필요한 데이터가 주어진다면")
        class Context_valid {

            final ProductSaveDto source = new ProductSaveDto(TEST_MAKER, TEST_PRICE, TEST_IMAGE_PATH);

            @BeforeEach
            void setUp() {
                Product product = new Product(1L, TEST_MAKER, TEST_PRICE, TEST_IMAGE_PATH);
                given(productCommandService.saveProduct(any(Product.class))).willReturn(product);
            }

            @Test
            @DisplayName("상품을 생성하고 리턴한다.")
            void it_save_and_return_product() {

                Product product = productController.save(source);

                assertAll(
                        () -> assertThat(product.getId()).isNotNull(),
                        () -> assertThat(product.getMaker()).isEqualTo(TEST_MAKER),
                        () -> assertThat(product.getPrice()).isEqualTo(TEST_PRICE),
                        () -> assertThat(product.getImagePath()).isEqualTo(TEST_IMAGE_PATH)
                );
            }
        }
    }
}
