package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("ProductRepository 클래스")
class ProductRepositoryTest {

    private static final Long PRODUCT_ID = 1L;
    @Autowired
    private ProductRepository productRepository;

    private int PRODUCTS_SIZE;

    @Nested
    @DisplayName("findAll")
    class Describe_findAll {

        @Nested
        @DisplayName("상품이 여러개 저장되어 있다면")
        class Context_with_products {
            @BeforeEach
            void setUpProducts() {
                generateProducts();
            }

            @Test
            @DisplayName("모든 상품 목록을 리턴한다")
            void it_returns_all_products() {

                List<Product> products = productRepository.findAll();
                assertThat(products).hasSize(2);
            }
        }

        @Nested
        @DisplayName("상품이 없다면")
        class Context_without_products {

            @Test
            @DisplayName("빈 목록을 리턴한다")
            void it_returns_all_products() {

                List<Product> products = productRepository.findAll();
                assertThat(products).hasSize(PRODUCTS_SIZE);
            }
        }
    }

    @Nested
    @DisplayName("findById")
    class Describe_findById {

        @BeforeEach
        void setUpProducts() {
            generateProducts();
        }

        @Nested
        @DisplayName("존재하는 상품 Id가 주어지면")
        class Context_with_existing_product_id {
            @Test
            @DisplayName("해당 상품을 리턴한다")
            void it_returns_a_product() {
                Optional<Product> product = productRepository.findById(PRODUCT_ID);
                assertThat(product.isPresent()).isTrue();
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품 Id가 주어지면")
        class Context_with_non_existing_product_id {
            @Test
            @DisplayName("빈 상품을 리턴한다")
            void it_returns_a_product() {
                Optional<Product> product = productRepository.findById(0L);
                assertThat(product.isPresent()).isFalse();
            }
        }
    }

    @Nested
    @DisplayName("save")
    class Describe_save {

        @DisplayName("상품을 저장하고 저장된 상품을 id와 리턴한다")
        @Test
        void it_saves_product_and_returns_with_id() {
            Product product = Product.builder()
                    .name("product")
                    .maker("maker")
                    .price(100)
                    .imageUrl("imageUrl")
                    .build();

            Product savedProduct = productRepository.save(product);

            assertThat(savedProduct).isEqualTo(product);
            assertThat(savedProduct.getId()).isNotNull();
        }
    }

    public void generateProducts() {
        Product product1 = Product.builder()
                .id(1L)
                .name("name 1")
                .imageUrl("imageURL 1")
                .maker("brand 1")
                .price(100)
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .name("name 2")
                .imageUrl("imageURL 2")
                .maker("brand 2")
                .price(200)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> products = productRepository.findAll();
        PRODUCTS_SIZE = products.size();
    }
}
