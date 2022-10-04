package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductJpaRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@RunWith(SpringRunner.class)
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;


    @DisplayName("findAll method")
    @Nested
    class Describe_findAll {


        @DisplayName("if nothing is added")
        @Nested
        class Context_no_product {

            @BeforeEach
            void setup() {
                productService.deleteAll();

            }
            @DisplayName("returns an empty list ")
            @Test
            void it_returns_empty() {
                List<Product> all = productService.findAll();
                assertThat(all.size()).isEqualTo(0);
            }
        }

        @DisplayName("if two products are added")
        @Nested
        class Context_two_products {

            @BeforeEach
            void setup() {
                productService.deleteAll();
            }

            @DisplayName("returns a list of two products ")
            @Test
            void it_returns_empty() {
                productService.save(Product.builder().name("name").maker("maker").imageUrl("url").build());
                productService.save(Product.builder().name("name2").maker("maker2").imageUrl("url2").build());

                List<Product> all = productService.findAll();
                assertThat(all.size()).isEqualTo(2);
            }
        }
    }

    @DisplayName("save method")
    @Nested
    class Describe_save {
        @After
        void after() {
            productService.deleteAll();
        }

        @DisplayName("if one product is saved")
        @Nested
        class Context_one_product {
            private Product product = Product.builder().name("name").maker("maker").imageUrl("url").price(1000L).build();
            private Product savedProduct;

            @BeforeEach
            void before() {
                savedProduct = productService.save(product);
            }

            @AfterEach
            void after() {
                productService.deleteAll();
            }

            @DisplayName("returns a product")
            @Test
            void it_returns_product() {
                assertThat(savedProduct.getName()).isEqualTo(product.getName());
                assertThat(savedProduct.getId()).isEqualTo(product.getId());
                assertThat(savedProduct.getImageUrl()).isEqualTo(product.getImageUrl());
            }

            @DisplayName("returns a list of products ")
            @Test
            void it_returns_list() {
                List<Product> all = productService.findAll();
                assertThat(all).hasSize(1);
            }
        }
    }


}
