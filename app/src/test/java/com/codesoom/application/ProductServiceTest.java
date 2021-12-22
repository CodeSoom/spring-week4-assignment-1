package com.codesoom.application;

import com.codesoom.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductService 클래스")
class ProductServiceTest {

    private ProductService productService;

    private static final String PRODUCT_NAME = "고양이 장남감";
    private static final String PRODUCT_MAKER = "코드숨";
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(10000);
    private static final String PRODUCT_IMAGE_URL = "test.jpg";

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Nested
    @DisplayName("GET 요청은")
    class Describe_get {
        @Nested
        @DisplayName("등록된 Product가 있다면")
        class Context_has_product {
            final int productCount = 3;

            @BeforeEach
            void setUp() {
                for (int i = 0; i < productCount; i++) {
                    productService.createProduct(getProduct());
                }
            }

            @Test
            @DisplayName("전체 리스트를 리턴한다.")
            void it_return_list() {
                assertThat(productService.getProducts()).isNotEmpty();
                assertThat(productService.getProducts()).hasSize(productCount);
            }
        }

        @Nested
        @DisplayName("등록된 Product가 없다면")
        class Context_none_product {
            final int productCount = 3;

            @BeforeEach
            void setUp() {
                List<Product> products = productService.getProducts();
                products.forEach(product -> productService.deleteProduct(product.getId()));
            }

            @Test
            @DisplayName("빈 리스트를 리턴한다.")
            void it_return_list() {
                assertThat(productService.getProducts()).isEmpty();
            }
        }
    }

    private Product getProduct() {
        Product product = new Product();

        product.setName(PRODUCT_NAME);
        product.setMaker(PRODUCT_MAKER);
        product.setPrice(PRODUCT_PRICE);
        product.setImageUrl(PRODUCT_IMAGE_URL);

        return product;
    }
}
