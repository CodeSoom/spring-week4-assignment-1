package com.codesoom.assignment.service;

import com.codesoom.assignment.Exception.ErrorCode;
import com.codesoom.assignment.Exception.ProductException;
import com.codesoom.assignment.domain.Product;
import org.junit.After;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest()
@RunWith(SpringRunner.class)
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private Product product1 = Product.builder()
            .name("name")
            .maker("maker")
            .imageUrl("url")
            .price(1000L)
            .build();
    private Product product2 = Product.builder()
            .name("name2")
            .maker("maker2")
            .imageUrl("url2")
            .build();

    @DisplayName("findAll method")
    @Nested
    class Describe_findAll {

        @DisplayName("if nothing is added")
        @Nested
        class Context_no_product {
            List<Product> productList = new ArrayList<>();

            @BeforeEach
            void setup() {
                productService.deleteAll();
            }

            @DisplayName("returns an empty list ")
            @Test
            void it_returns_empty() {
                List<Product> all = productService.findAll();
                assertThat(all.size()).isEqualTo(productList.size());
            }
        }

        @DisplayName("if two products are added")
        @Nested
        class Context_two_products {
            List<Product> productList = new ArrayList<>();

            @BeforeEach
            void setup() {
                productService.deleteAll();
                productList.add(productService.save(product1));
                productList.add(productService.save(product2));

            }

            @DisplayName("returns a list of products ")
            @Test
            void it_returns_two_products() {
                List<Product> all = productService.findAll();
                assertThat(all.size()).isEqualTo(productList.size());
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
            private Product product = Product.builder()
                    .name("name")
                    .maker("maker")
                    .imageUrl("url")
                    .price(1000L)
                    .build();

            private List<Product> productList = new ArrayList<>();


            @BeforeEach
            void before() {
                productList.add(productService.save(product));
            }

            @AfterEach
            void after() {
                productService.deleteAll();
                productList.clear();
            }

            @DisplayName("returns a product")
            @Test
            void it_returns_product() {
                assertThat(productList.get(0).getName())
                        .isEqualTo(product.getName());

                assertThat(productList.get(0).getId())
                        .isEqualTo(product.getId());

                assertThat(productList.get(0).getImageUrl())
                        .isEqualTo(product.getImageUrl());
            }

            @DisplayName("returns a list of products ")
            @Test
            void it_returns_list() {
                List<Product> all = productService.findAll();
                assertThat(all).hasSize(productList.size());
            }
        }
    }

    @DisplayName("findById Method")
    @Nested
    class Describe_findById {

        @DisplayName("if a valid id is given,")
        @Nested
        class Context_one_product {
            Product product;

            @BeforeEach
            void before() {
                product = productService.save(product1);
            }

            @AfterEach
            void after() {
                productService.deleteAll();
            }

            @DisplayName("returns the found task")
            @Test
            void it_returns_product() throws ProductException {
                Product foundProduct = productService.findById(product.getId());

                assertThat(foundProduct.getId())
                        .isEqualTo(product1.getId());
                assertThat(foundProduct.getImageUrl())
                        .isEqualTo(product1.getImageUrl());
                assertThat(foundProduct.getName())
                        .isEqualTo(product1.getName());
                assertThat(foundProduct.getMaker())
                        .isEqualTo(product1.getMaker());
                assertThat(foundProduct.getPrice())
                        .isEqualTo(product1.getPrice());
            }

            @DisplayName("throws an exception if the invalid id is given")
            @Test
            void it_throws_exception() throws ProductException {
                assertThatThrownBy(() -> {
                    productService.findById(123L);
                }).isInstanceOf(ProductException.class)
                        .hasMessageContaining("not found");


            }
        }
    }

    @DisplayName("deleteAll method")
    @Nested
    class Describe_deleteAll {

        @DisplayName("if two products are added")
        @Nested
        class Context_two_products {
            List<Product> productList = new ArrayList<>();
            @BeforeEach
            void before() {
                productList.add(productService.save(product1));
                productList.add(productService.save(product2));
            }

            @DisplayName("returns an empty list if everything is deleted")
            @Test
            void it_returns_empty() {
                productService.deleteAll();
                productList.clear();
                assertThat(productList.size()).isEqualTo(productService.findAll().size());
            }
        }
    }
}
