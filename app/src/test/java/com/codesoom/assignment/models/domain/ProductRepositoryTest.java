package com.codesoom.assignment.models.domain;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@DisplayName("ProductRepository에 대한 단위테스트")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_list {

        @Nested
        @DisplayName("만약 두개의 상품이 등록되어있다면")
        class Context_contain_two_product {

            @BeforeEach
            void setUp() {
                Product toy1 = new Product(1L, "Test Toy1", "Test Maker1", 1000, null);
                Product toy2 = new Product(2L, "Test Toy2", "Test Maker2", 2000, null);
                productRepository.save(toy1);
                productRepository.save(toy2);
            }

            @Test
            @DisplayName("두개의 상품이 들어있는 목록을 반환합니다.")
            void it_return_list_contain_two_product() throws Exception {

                List<Product> productList = productRepository.findAll();
                Assertions.assertThat(productList).hasSize(2);

            }

        }

    }

    @Nested
    @DisplayName("findProductById 메소드는")
    class Describe_findProductById {

        @Nested
        @DisplayName("목록에 있는 상품을 조회할 경우")
        class Context_vallid_product {

            @BeforeEach
            void setUp() {
                Product toy1 = new Product(1L, "Test Toy1", "Test Maker1", 1000, null);
                productRepository.save(toy1);
            }

            @Test
            @DisplayName("조회한 상품을 반환합니다.")
            void it_return_list_found_product() {

                Product foundProduct = productRepository.findProductById(1L).get();
                Assertions.assertThat(foundProduct.getId()).isEqualTo(1L);
                Assertions.assertThat(foundProduct.getName()).isEqualTo("Test Toy1");
                Assertions.assertThat(foundProduct.getMaker()).isEqualTo("Test Maker1");
                Assertions.assertThat(foundProduct.getPrice()).isEqualTo(1000);

            }

        }

    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {

        @Nested
        @DisplayName("새로운 상품을 등록하는 경우")
        class Context_save_new_product {

            private Product newProduct;

            @BeforeEach
            void setUp() {
                newProduct = new Product(1L, "Test Toy1", "Test Maker1", 1000, null);

            }

            @Test
            @DisplayName("등록한 상품을 반환합니다.")
            void it_return_saved_product() {

                Product savedProduct = productRepository.save(newProduct);

                Assertions.assertThat(savedProduct).isNotNull();
                Assertions.assertThat(savedProduct.getName()).isEqualTo("Test Toy1");
                Assertions.assertThat(savedProduct.getMaker()).isEqualTo("Test Maker1");
                Assertions.assertThat(savedProduct.getPrice()).isEqualTo(1000);

            }

        }

    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

        @Nested
        @DisplayName("상품을 목록에서 삭제하는 경우")
        class Context_delete_product {

            Product deletedToy;

            @BeforeEach
            void setUp() {
                Product toy1 = new Product(1L, "Test Toy1", "Test Maker1", 1000, null);
                Product toy2 = new Product(2L, "Test Toy2", "Test Maker2", 2000, null);
                deletedToy = new Product(3L, "Test Toy3", "Test Maker3", 3000, null);
                productRepository.save(toy1);
                productRepository.save(toy2);
                productRepository.save(deletedToy);

            }

            @Test
            @DisplayName("해당하는 상품이 목록에서 삭제됩니다.")
            void it_delete_product_to_list() {

                // 현재 3개의 상품이 등록되어 있다.
                Assertions.assertThat(productRepository.findAll()).hasSize(3);

                productRepository.delete(deletedToy);

                // 삭제 후 2개의 상품만 남아있다.
                Assertions.assertThat(productRepository.findAll()).hasSize(2);

            }

        }

    }

}
