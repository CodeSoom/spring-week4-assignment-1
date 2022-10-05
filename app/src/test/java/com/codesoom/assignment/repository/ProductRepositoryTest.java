package com.codesoom.assignment.repository;

import com.codesoom.assignment.entity.Product;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ProductRepository")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_find_all {

        @BeforeEach
        void setUp() {
            productRepository.deleteAll();
        }

        @Nested
        @DisplayName("저장된 product가 있다면")
        class Context_have_product {

            @BeforeEach
            void prepare() {
                Product toy = new Product(1L, "테스트1", 2000, "");
                productRepository.save(toy);
            }

            @Test
            @DisplayName("ProductList를 리턴한다")
            void it_returns_productList() {
                assertThat(productRepository.findAll()).hasSize(1);
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_find_by_id {

        @BeforeEach
        void setUp() {
            productRepository.deleteAll();
        }

        @Nested
        @DisplayName("id가 존재하지 않는다면")
        class Context_non_existent_id {

            @BeforeEach
            void prepare() {
                Product source = new Product(1L, "테스트", 2000, "");
                productRepository.save(source);
                List<Product> all = productRepository.findAll();
                for(Product p : all) {
                    if (p.getId().equals(1L)){
                        productRepository.deleteById(1L);
                    }
                }
            }

            @Test
            @DisplayName("비어있는 Optional을 리턴한다")
            void it_returns_empty_productList() {
                assertThat(productRepository.findById(1L)).isEqualTo(Optional.empty());
            }
        }

        @Nested
        @DisplayName("id가 존재한다면")
        class Context_existent_id {

            Product source;
            Product saved;

            @BeforeEach
            void givenId() {
                source = new Product(1L, "테스트1", 2000, "");
                saved = productRepository.save(source);
            }

            @Test
            @DisplayName("해당 id를 가진 Product객체를 리턴한다")
            void it_returns_productList() {
                Product findProduct = productRepository.findById(saved.getId()).get();
                Assertions.assertEquals(findProduct.getBrand(), source.getBrand());
                Assertions.assertEquals(findProduct.getPrice(), source.getPrice());
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {

        @BeforeEach
        void setUp() {
            productRepository.deleteAll();
        }

        @Nested
        @DisplayName("Product객체가 주어질 때")
        class Context_existent_product {

            Product product = new Product(1L, "테스트1", 2000, "");

            @Test
            @DisplayName("Product객체를 저장 후 리턴한다")
            void it_returns_product() {
                Product saved = productRepository.save(product);
                assertThat(saved.getBrand()).isEqualTo(product.getBrand());
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_delete_by_id {

        @BeforeEach
        void setUp() {
            productRepository.deleteAll();
        }

        @Nested
        @DisplayName("id가 존재한다면")
        class Context_existent_id {

            @BeforeEach
            void givenProduct() {
                Product product = new Product(1L, "테스트1", 2000, "");
                productRepository.save(product);
            }

            @Test
            @DisplayName("해당 id를 가진 Product객체를 삭제한다")
            void it_returns_product() {
                productRepository.deleteById(1L);
                assertThat(productRepository.findAll()).hasSize(0);
            }
        }
    }
}
