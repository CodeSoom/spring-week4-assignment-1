package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductCommandService 클래스")
class ProductCommandServiceTest {

    private static final String TEST_MAKER = "MAKER";
    private static final Integer TEST_PRICE = 1000;
    private static final String TEST_IMAGE_PATH = "/image/test.jpg";

    @InjectMocks
    ProductCommandService productCommandService;

    @Mock
    ProductRepository productRepository;

    @Nested
    @DisplayName("saveProduct 메소드는")
    class Describe_saveProduct {

        @Nested
        @DisplayName("상품 등록에 필요한 데이터가 주어진다면")
        class Context_valid {

            final Product source = new Product(TEST_MAKER, TEST_PRICE, TEST_IMAGE_PATH);

            @BeforeEach
            void setUp() {

                Product product = new Product(1L, TEST_MAKER, TEST_PRICE, TEST_IMAGE_PATH);

                given(productRepository.save(any(Product.class))).willReturn(product);
            }

            @Test
            @DisplayName("상품을 등록하고 리턴한다.")
            void it_save_product_and_return() {

                Product product = productCommandService.saveProduct(source);

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
