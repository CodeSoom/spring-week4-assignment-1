package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import org.junit.Before;
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

            @Before
            void setup() {
            }

            @DisplayName("returns an empty list ")
            @Test
            void it_returns_empty() {
                List<Product> all = productService.findAll();
                assertThat(all.size()).isEqualTo(0);
            }
        }
    }


    @DisplayName("save method")
    @Nested
    class Describe_save {

        @DisplayName("if one product is saved")
        @Nested
        class Context_one_product {
            private Product product = Product.builder().name("name").maker("maker").imageUrl("url").price(1000L).build();
            private Product savedProduct = productService.save(product);


            @DisplayName("returns a product")
            @Test
            void it_returns_product() {
                assertThat(savedProduct.getName()).isEqualTo(product.getName());
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
