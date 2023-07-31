package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.dto.ProductRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ProductUpdate 클래스")
public class ProductUpdaterTest extends JpaTest{
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
    class updateProduct_메서드는 {
        ProductUpdater productUpdater = new ProductUpdater(productRepository);

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 수정할_상품이_없다면 {
            @BeforeEach
            void setUp() {
                productRepository.deleteAll();
            }

            @Test
            @DisplayName("ProductNotFoundException을_반환한다")
            void ProductNotFoundException을_반환한다() {
                Assertions.assertThatThrownBy(() -> productUpdater.updateProduct(1L, null))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 수정할_상품이_있다면 {
            private Product product;
            @BeforeEach
            void setUp() {
                productRepository.deleteAll();
                product = createProduct();
                productRepository.save(product);

            }

            @Test
            @DisplayName("해당_상품정보를_수정한_후_해당_상품을_반환한다")
            void 해당_상품정보를_수정한_후_해당_상품을_반환한다() {
                ProductRequest productRequest = new ProductRequest("updateProduct", "updateMaker", 2000, "updateImage");
                Product updatedProduct = productUpdater.updateProduct(product.getId(), productRequest);

                Assertions.assertThat(updatedProduct.getName()).isEqualTo(productRequest.getName());
                Assertions.assertThat(updatedProduct.getMaker()).isEqualTo(productRequest.getMaker());
                Assertions.assertThat(updatedProduct.getPrice()).isEqualTo(productRequest.getPrice());
                Assertions.assertThat(updatedProduct.getImageUrl()).isEqualTo(productRequest.getImageUrl());
            }
        }
    }

}
