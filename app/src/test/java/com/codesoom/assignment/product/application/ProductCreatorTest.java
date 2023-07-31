package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.dto.ProductRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ProductCreator 클래스")
public class ProductCreatorTest extends JpaTest{
    private ProductRequest createProductRequest() {
        return new ProductRequest("테스트 상품", "테스트 제조사", 1000, "테스트 이미지 URL");
    }
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createProduct_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품요청정보가_주어지면 {
            private ProductRequest PRODUCT_REQUEST;
            @BeforeEach
            void setUp() {
                PRODUCT_REQUEST = createProductRequest();
            }
            @Test
            @DisplayName("해당상품정보를_저장후_해당상품정보를_리턴한다")
            void  해당상품정보를_저장후_해당상품정보를_리턴한다() {
                ProductCreator productCreator = new ProductCreator(productRepository);
                Product product = productCreator.createProduct(PRODUCT_REQUEST);
                Assertions.assertThat(product.getName()).isEqualTo(PRODUCT_REQUEST.getName());
                Assertions.assertThat(product.getMaker()).isEqualTo(PRODUCT_REQUEST.getMaker());
                Assertions.assertThat(product.getPrice()).isEqualTo(PRODUCT_REQUEST.getPrice());
                Assertions.assertThat(product.getImageUrl()).isEqualTo(PRODUCT_REQUEST.getImageUrl());
            }

        }




    }

}
