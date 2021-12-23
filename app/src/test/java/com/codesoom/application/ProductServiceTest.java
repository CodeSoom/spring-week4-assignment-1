package com.codesoom.application;

import com.codesoom.domain.Product;
import com.codesoom.domain.ProductRepository;
import com.codesoom.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ProductService 클래스")
class ProductServiceTest {

    private ProductService productService;

    private static final String PRODUCT_NAME = "고양이 장남감";
    private static final String UPDATE_POSTFIX = "new";
    private static final String PRODUCT_MAKER = "코드숨";
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(10000);
    private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(100);
    private static final String PRODUCT_IMAGE_URL = "test.jpg";


    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new ProductRepository();

        productService = new ProductService(productRepository);
    }

    @Nested
    @DisplayName("GET 메소드는")
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

        @Nested
        @DisplayName("등록된 id 값이 주어졌을때")
        class Context_when_product_is_exist {
            Product product;

            @BeforeEach
            void setUp() {
                product = productService.createProduct(getProduct());
            }

            @Test
            @DisplayName("등록된 Product 정보를 리턴한다.")
            void it_return_product() {
                assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
                assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
                assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
                assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL);
            }
        }

        @Nested
        @DisplayName("id에 해당하는 Product가 존재하지 않으면")
        class Context_when_product_isnot_exist {

            @Test
            @DisplayName("Product를 찾을 수 없다는 예외를 던진다.")
            void it_return_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.getProduct(0L)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("Update 메소드는")
    class Describe_update {
        Product product;

        @BeforeEach
        void setUp() {
            product = productService.createProduct(getProduct());
        }

        @Nested
        @DisplayName("수정할 id가 주어진다면")
        class Context_when_product_is_exist {
            @Test
            @DisplayName("id에 해당하는 Product 정보를 수정하고 리턴한다.")
            void it_fix_product_return() {
                Product source = new Product();

                source.setName(UPDATE_POSTFIX + PRODUCT_NAME);
                source.setMaker(UPDATE_POSTFIX + PRODUCT_MAKER);
                source.setImageUrl(UPDATE_POSTFIX + PRODUCT_IMAGE_URL);
                source.setPrice(NEW_PRICE);

                productService.updateProduct(source, 1L);

                Optional<Product> product = productService.getProduct(1L);

                assertThat(product.get().getName()).isEqualTo(UPDATE_POSTFIX + PRODUCT_NAME);
                assertThat(product.get().getMaker()).isEqualTo(UPDATE_POSTFIX + PRODUCT_MAKER);
                assertThat(product.get().getImageUrl()).isEqualTo(UPDATE_POSTFIX + PRODUCT_IMAGE_URL);
                assertThat(product.get().getPrice()).isEqualTo(NEW_PRICE);
            }
        }
    }

    @Nested
    @DisplayName("Delete 메소드는")
    class Describe_delete {

        @BeforeEach
        void setUp() {
            productService.createProduct(getProduct());
        }

        @Nested
        @DisplayName("등록된 id가 주어진다면")
        class Context_when_product_is_exist {
            @Test
            @DisplayName("id에 해당하는 Product가 삭제되고 남은 상품 리스트를 리턴한다.")
            void it_return_products() {
                List<Product> products = productService.deleteProduct(1L);
                assertThat(products.size()).isEqualTo(0);
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
