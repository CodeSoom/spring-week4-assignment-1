package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {

    private ProductService productService;
    private Product product;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
        product = Product.builder()
            .name("name")
            .maker("maker")
            .price(10000L)
            .imageUrl("imageUrl")
            .build();

        productRepository.save(product);
    }

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_createProduct {

        @Nested
        @DisplayName("상품 객체가 주어지면")
        class Context_passProduct {

            Product targetProduct;

            @BeforeEach
            void setUp() {
                targetProduct = product;
            }

            @Test
            @DisplayName("생성된 상품을 리턴한다")
            void it_create() {
                Product createdProduct = productService.createProduct(targetProduct);

                assertThat(createdProduct).isEqualTo(targetProduct);
            }
        }
    }

    @Nested
    @DisplayName("getAllProducts 메서드는")
    class Describe_getAllProducts {

        @Test
        @DisplayName("모든 상품을 리턴한다")
        void it_returns_all_products() {
            Iterable<Product> allProducts = productService.getAllProducts();

            ArrayList<Product> products = Lists.newArrayList(allProducts);

            assertThat(products).hasSize(1);
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {

        @Nested
        @DisplayName("존재하는 상품일 경우")
        class Context_existProduct {

            private Long existProductId;

            @BeforeEach
            void setUp() {
                Long productId = product.getId();

                assertThat(productRepository.findById(productId))
                    .isNotEqualTo(Optional.empty());

                existProductId = productId;
            }

            @Test
            @DisplayName("찾은 상품을 리턴한다")
            void it_return_found_product() {
                Product foundProduct = productService.getProduct(existProductId);

                assertThat(foundProduct).isEqualTo(product);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품일 경우")
        class Context_notExistProduct {

            private Long notExistProductId;

            @BeforeEach
            void setUp() {
                productRepository.deleteAll();

                notExistProductId = product.getId();
            }

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() -> productService.getProduct(notExistProductId))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_updateProduct {

        private Product source;

        @BeforeEach
        void setUp() {
            source = Product.builder()
                .name("a")
                .maker("b")
                .price(100L)
                .imageUrl("c")
                .build();
        }

        @Nested
        @DisplayName("존재하는 상품일 경우")
        class Context_existProduct {

            private Long existProductId;

            @BeforeEach
            void setUp() {
                Long productId = product.getId();

                assertThat(productRepository.findById(productId))
                    .isNotEqualTo(Optional.empty());

                existProductId = productId;
            }

            @Test
            @DisplayName("수정한 상품을 리턴한다")
            void it_return_updated_product() {
                Product updatedProduct = productService.updateProduct(existProductId, source);

                assertThat(source).isEqualTo(updatedProduct);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품일 경우")
        class Context_notExistProduct {

            private Long notExistProductId;

            @BeforeEach
            void setUp() {
                productRepository.deleteAll();

                notExistProductId = product.getId();
            }

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() -> productService.updateProduct(notExistProductId, source))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_deleteProduct {

        @Nested
        @DisplayName("존재하는 상품일 경우")
        class Context_existProduct {

            private Long existProductId;

            @BeforeEach
            void setUp() {
                Long productId = product.getId();

                assertThat(productRepository.findById(productId))
                    .isNotEqualTo(Optional.empty());

                existProductId = productId;
            }

            @Test
            @DisplayName("삭제한다")
            void it_delete() {
                productService.deleteProduct(existProductId);

                Optional<Product> findFromId = productRepository.findById(existProductId);

                assertThat(findFromId.isPresent()).isFalse();
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품일 경우")
        class Context_notExistProduct {

            private Long notExistProductId;

            @BeforeEach
            void setUp() {
                productRepository.deleteAll();

                notExistProductId = product.getId();
            }

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() -> productService.deleteProduct(notExistProductId))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
