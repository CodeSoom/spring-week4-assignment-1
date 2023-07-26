package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.dto.ProductRequest;
import com.codesoom.assignment.product.infra.persistence.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ProductReader 클래스")
public class ProductReaderTest extends JpaTest{
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class getProductList_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 저장된_상품이_없다면 {
            @BeforeEach
            void setUp() {
                productRepository.deleteAll();
            }
            @Test
            @DisplayName("빈 리스트를_리턴한다")
            void 빈_리스트를_리턴한다() {
                ProductReader productReader = new ProductReader(productRepository);
                Assertions.assertThat(productReader.getProductList()).isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 저장된_상품이_있다면 {
            private final String PRODUCT_NAME = "testProduct";
            private final String PRODUCT_IMAGE_URL = "testImage";
            private final int PRODUCT_PRICE = 1000;
            private final String PRODUCT_MAKER = "testMaker";

            @BeforeEach
            void setUp() {
                productRepository.deleteAll();
            }

            private Product createProduct() {
                Product testProduct = Product.builder()
                        .name(PRODUCT_NAME)
                        .imageUrl(PRODUCT_IMAGE_URL)
                        .price(PRODUCT_PRICE)
                        .maker(PRODUCT_MAKER)
                        .build();
                return testProduct;
            }

            @Test
            @DisplayName("저장된_상품리스트를_리턴한다")
            void 저장된_상품리스트를_리턴한다() {
                Product testProduct = createProduct();
                productRepository.save(testProduct);


                ProductReader productReader = new ProductReader(productRepository);
                Assertions.assertThat(productReader.getProductList()).isNotEmpty();
                Product product = productReader.getProductList().iterator().next();
                Assertions.assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
                Assertions.assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL);
                Assertions.assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
                Assertions.assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
            }
        }
    }
}
