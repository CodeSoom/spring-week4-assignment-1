package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductSaveDto;
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
@DisplayName("CatToyService 클래스")
class ProductServiceTest {

    private static final String TEST_MAKER = "MAKER";
    private static final Integer TEST_PRICE = 1000;
    private static final String TEST_IMAGE_PATH = "/image/test.jpg";

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {

        @Nested
        @DisplayName("주어진 상품 수 만큼")
        class Context_hasProducts {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {
                Iterable<Product> products = LongStream.rangeClosed(1, givenCount)
                        .mapToObj(Product::new)
                        .collect(Collectors.toList());

                given(productRepository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("고양이 장난감 목록을 리턴한다.")
            void it_return_products() {

                List<Product> products = productService.getProducts();

                assertThat(products).hasSize(givenCount);
            }
        }
    }

    @Nested
    @DisplayName("saveProduct 메소드는")
    class Describe_saveProduct {

        @Nested
        @DisplayName("상품 등록에 필요한 데이터가 주어진다면")
        class Context_valid {

            final ProductSaveDto source = new ProductSaveDto(TEST_MAKER, TEST_PRICE, TEST_IMAGE_PATH);

            @BeforeEach
            void setUp() {

                Product product = new Product(1L, TEST_MAKER, TEST_PRICE, TEST_IMAGE_PATH);

                given(productRepository.save(any(Product.class))).willReturn(product);
            }

            @Test
            @DisplayName("상품을 등록하고 리턴한다.")
            void it_save_product_and_return() {

                Product product = productService.saveProduct(source);

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
