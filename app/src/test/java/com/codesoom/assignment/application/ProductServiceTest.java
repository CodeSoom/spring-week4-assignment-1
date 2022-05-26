package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductCommandRequest;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    private ProductService productService;

    public static final String NAME = "털뭉치";
    public static final String MAKER = "애옹이네 장난감";
    public static final int PRICE = 2000;
    public static final String IMAGE_URL = "https://cdn.pixabay.com/photo/2018/10/05/12/09/animal-3725762_960_720.jpg";

    Product product;
    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
        product = new Product(NAME, MAKER, PRICE, IMAGE_URL);
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {
        ProductCommandRequest productCommandRequest;

        @BeforeEach
        void setUp() {
            productCommandRequest = ProductCommandRequest.builder()
                    .name(NAME)
                    .maker(MAKER)
                    .price(PRICE)
                    .imageUrl(IMAGE_URL).build();
        }

        @Test
        @DisplayName("생성된 product의 dto를 반환한다.")
        void it_returns_created_product() {
            ProductResponse newProduct = productService.createTask(productCommandRequest);

            assertThat(newProduct.getId()).isNotNull();
            assertThat(newProduct.getName()).isEqualTo(NAME);
            assertThat(newProduct.getMaker()).isEqualTo(MAKER);
            assertThat(newProduct.getPrice()).isEqualTo(PRICE);
            assertThat(newProduct.getImageUrl()).isEqualTo(IMAGE_URL);
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {
        private static final String UPDATE_NAME = "캣닢";
        private static final String UPDATE_MAKER = "캣드러그";
        private static final int UPDATE_PRICE = 7000;
        private static final String UPDATE_IMAGE_URL = "https://www.catdrug.com/images/1234567";

        private Long id;
        private ProductCommandRequest productCommandRequest;

        @BeforeEach
        void setUp() {
            productCommandRequest = ProductCommandRequest.builder()
                    .name(UPDATE_NAME)
                    .maker(UPDATE_MAKER)
                    .price(UPDATE_PRICE)
                    .imageUrl(UPDATE_IMAGE_URL)
                    .build();
        }

        @DataJpaTest
        @Nested
        @DisplayName("존재하는 id가 주어지면")
        class Context_with_existed_id {

            @BeforeEach
            void setUp() {
                Product newProduct = productRepository.save(product);
                id = newProduct.getId();
            }

            @Test
            @DisplayName("product를 변경한다.")
            void it_updates_product() {
                productService.updateProduct(id, productCommandRequest);

                Product updatedProduct = productRepository.findById(id).get();

                assertThat(updatedProduct.getName()).isEqualTo(UPDATE_NAME);
                assertThat(updatedProduct.getMaker()).isEqualTo(UPDATE_MAKER);
                assertThat(updatedProduct.getPrice()).isEqualTo(UPDATE_PRICE);
                assertThat(updatedProduct.getImageUrl()).isEqualTo(UPDATE_IMAGE_URL);
            }
        }

        @DataJpaTest
        @Nested
        @DisplayName("존재하지 않는 id가 주어지면")
        class Context_with_non_existed_id {

            @BeforeEach
            void setUp() {
                id = 100L;
            }

            @Test
            @DisplayName("ProductNotFoundExcpetion이 발생한다.")
            void it_throws_product_not_found_exception() {
                assertThatThrownBy(() -> productService.updateProduct(id, productCommandRequest))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Context_getProduct {
        private Long id = 1L;

        public ProductResponse subject() {
            productRepository.save(product);
            return productService.getProduct(id);
        }
        @Test
        @DisplayName("producctResponse를 반환한다.")
        void it_returns_productResponse() {
            ProductResponse productResponse = subject();

            assertThat(productResponse.getId()).isEqualTo(product.getId());
            assertThat(productResponse.getMaker()).isEqualTo(product.getMaker());
            assertThat(productResponse.getImageUrl()).isEqualTo(product.getImageUrl());
            assertThat(productResponse.getPrice()).isEqualTo(product.getPrice());
            assertThat(productResponse.getName()).isEqualTo(product.getName());
        }
    }
}
