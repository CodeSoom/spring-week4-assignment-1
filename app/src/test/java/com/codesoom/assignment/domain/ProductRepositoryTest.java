package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("ProductRepository 클래스")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {

    }

    @Nested
    @DisplayName("findAll")
    class Describe_findAll {

        @Nested
        @DisplayName("상품이 여러개 저장되어 있다면")
        class Context_with_products {
            @BeforeEach
            void setUpProducts() {
                Product product1 = Product.builder()
                        .name("product 1")
                        .maker("maker 1")
                        .price(100)
                        .image("imageUrl 1")
                        .build();

                Product product2 = Product.builder()
                        .name("product 2")
                        .maker("maker 2")
                        .price(200)
                        .image("imageUrl 2")
                        .build();

                productRepository.save(product1);
                productRepository.save(product2);
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
                assertThat(products).hasSize(2);
            }
        }
    }
}
