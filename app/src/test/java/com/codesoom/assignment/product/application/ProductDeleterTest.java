package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Optional;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ProductDeleter 클래스")
public class ProductDeleterTest extends JpaTest {
    private final String PRODUCT_NAME = "testProduct";
    private final String PRODUCT_IMAGE_URL = "testImage";
    private final int PRODUCT_PRICE = 1000;
    private final String PRODUCT_MAKER = "testMaker";

    private Product createProduct() {
        Product testProduct = Product.builder()
                .name(PRODUCT_NAME)
                .imageUrl(PRODUCT_IMAGE_URL)
                .price(PRODUCT_PRICE)
                .maker(PRODUCT_MAKER)
                .build();
        return testProduct;
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class deleteProduct_메서드는 {
        ProductDeleter productDeleter = new ProductDeleter(productRepository);

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 해당하는_상품이_없다면 {
            @Test
            @DisplayName("ProductNotFoundException을_반환한다")
            void ProductNotFoundException을_반환한다() {
                Assertions.assertThatThrownBy(() -> productDeleter.deleteProduct(1L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 해당하는_상품이_있다면 {
            @Test
            @DisplayName("해당하는_상품을_삭제한다")
            void 해당하는_상품을_삭제한다() {
                Product product = createProduct();
                productRepository.save(product);
                productDeleter.deleteProduct(product.getId());
                Assertions.assertThat(productRepository.findById(product.getId())).isEmpty();
            }
        }
    }
}
