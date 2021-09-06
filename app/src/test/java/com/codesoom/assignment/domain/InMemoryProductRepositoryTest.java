package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductRepositoryTest 클래스")
class InMemoryProductRepositoryTest {

    private InMemoryProductRepository inMemoryProductRepository;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        product1 = new Product(1L, "toy1", "maker1", 1000L, "toy1.jpg");
        product2 = new Product(2L, "toy2", "maker2", 2000L, "toy2.jpg");
        product3 = new Product(3L, "toy3", "maker3", 3000L, "toy3.jpg");

        inMemoryProductRepository = new InMemoryProductRepository();
    }

    @Nested
    @DisplayName("save 메소드")
    class Describe_save {

        @Test
        @DisplayName("product를 Repository에 저장합니다.")
        void it_add_product() {
            assertThat(inMemoryProductRepository.findAll()).hasSize(0);
            inMemoryProductRepository.save(product1);
            assertThat(inMemoryProductRepository.findAll()).hasSize(1);
        }

        @Test
        @DisplayName("새로 저장된 product를 반환합니다.")
        void it_return_new_product() {
            Product newProduct = inMemoryProductRepository.save(product1);
            assertThat(newProduct.getName()).isEqualTo(product1.getName());
            assertThat(newProduct.getMaker()).isEqualTo(product1.getMaker());
            assertThat(newProduct.getPrice()).isEqualTo(product1.getPrice());
            assertThat(newProduct.getImageUrl()).isEqualTo(product1.getImageUrl());
        }
    }

    @Nested
    @DisplayName("findAll 메소드")
    class Describe_findAll {

        @Nested
        @DisplayName("product 데이터가 없다면")
        class Context_has_no_product {

            @Test
            @DisplayName("빈 데이터를 반환합니다.")
            void it_return_empty_products() {
                List<Product> products = inMemoryProductRepository.findAll();

                assertThat(products).hasSize(0);
            }
        }

        @Nested
        @DisplayName("product 데이터가 저장되어 있다면")
        class Context_has_products {

            private List<Product> givenProducts;

            @BeforeEach
            void prepare() {
                givenProducts = List.of(product1, product2, product3);

                for (Product product: givenProducts) {
                    inMemoryProductRepository.save(product);
                }
            }

            @Test
            @DisplayName("저장되어 있는 product를 반환합니다.")
            void it_return_all_products() {
                int index = 0;
                List<Product> products = inMemoryProductRepository.findAll();

                assertThat(products).hasSize(3);

                for (Product product: givenProducts) {
                    assertThat(products.get(index)).isEqualTo(product);
                    index ++;
                }

            }
        }
    }

    @Nested
    @DisplayName("findById 메소드")
    class Describe_findById {

        private Long valid_id;

        @BeforeEach
        void prepare() {
            valid_id = product1.getId();
            inMemoryProductRepository.save(product1);
        }

        @Test
        @DisplayName("해당 Id의 Optional<Product>를 반환합니다.")
        void it_return_optional_product() {
            assertThat(inMemoryProductRepository.findById(valid_id)).isEqualTo(Optional.of(product1));
        }
    }

    @Nested
    @DisplayName("update 메소드")
    class Describe_update {

        @BeforeEach
        void prepare() {
            inMemoryProductRepository.save(product1);
        }

        @Nested
        @DisplayName("해당되는 id의 product가 있다면")
        class Context_with_valid_id {

            private Long valid_id;

            @BeforeEach
            void prepare_valid_id() {
                valid_id = product1.getId();
            }

            @Test
            @DisplayName("업데이트된 product를 반환합니다.")
            void it_return_product() {
                Product updatedProduct = inMemoryProductRepository.update(valid_id, product2);

                assertThat(updatedProduct.getName()).isEqualTo(product2.getName());
                assertThat(updatedProduct.getMaker()).isEqualTo(product2.getMaker());
                assertThat(updatedProduct.getPrice()).isEqualTo(product2.getPrice());
                assertThat(updatedProduct.getImageUrl()).isEqualTo(product2.getImageUrl());
            }
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private Long invalid_id;

            @BeforeEach
            void prepare_invalid_id() {
                invalid_id = product2.getId();
            }

            @Test
            @DisplayName("ProductNotFoundException을 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> inMemoryProductRepository.update(invalid_id, product2))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드")
    class Describe_deleteById {

        @BeforeEach
        void prepare() {
            inMemoryProductRepository.save(product1);
        }

        @Nested
        @DisplayName("해당되는 id의 product가 있다면")
        class Context_with_valid_id {

            private Long valid_id;

            @BeforeEach
            void prepare_valid_id() {
                valid_id = product1.getId();
            }

            @Test
            @DisplayName("해당 Id의 product를 제거합니다.")
            void it_update_product() {
                inMemoryProductRepository.deleteById(valid_id);

                assertThat(inMemoryProductRepository.findAll()).hasSize(0);
            }
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private Long invalid_id;

            @BeforeEach
            void prepare_invalid_id() {
                invalid_id = product2.getId();
            }

            @Test
            @DisplayName("ProductNotFoundException을 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> inMemoryProductRepository.update(invalid_id, product2))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}