package com.codesoom.assignment.domain;

import org.junit.jupiter.api.AfterEach;
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

@DisplayName("ProductRepository 클래스")
@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void clear() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        @Nested
        @DisplayName("데이터가 존재한다면")
        class Context_with_existed_data {
            private final List<Product> givenProducts = new ArrayList<>();

            @BeforeEach
            void prepare() {
                final Product product1 = Product.builder()
                        .name("고양이장난감1")
                        .maker("삼성")
                        .price(50000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                givenProducts.add(productRepository.save(product1));

                final Product product2 = Product.builder()
                        .name("고양이장난감2")
                        .maker("애플")
                        .price(80000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                givenProducts.add(productRepository.save(product2));
            }

            @Test
            @DisplayName("모든 장난감을 리턴한다")
            void it_returns_all_toy() {
                final List<Product> actualProducts = productRepository.findAll();

                assertThat(actualProducts).hasSize(givenProducts.size());
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_existed_id {
            private Product givenProduct;

            @BeforeEach
            void prepare() {
                final Product product = Product.builder()
                        .name("고양이장난감1")
                        .maker("삼성")
                        .price(50000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                givenProduct = productRepository.save(product);
            }

            @Test
            @DisplayName("고양이 장난감을 찾아서 리턴한다")
            void it_returns_searched_cat_toy() {
                final Product actualProduct = productRepository.findById(givenProduct.getId()).orElse(null);

                assertThat(actualProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(actualProduct.getId()).isEqualTo(givenProduct.getId());
                assertThat(actualProduct.getPrice()).isEqualTo(givenProduct.getPrice());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 ID가 주어지면")
        class Context_with_non_existed_id {
            @Test
            @DisplayName("Optional.empty()를 리턴한다")
            void it_returns_optional_empty() {
                assertThat(productRepository.findById(100L)).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("새로운 고양이 장난감이 주어지면")
        class Context_with_new_cat_toy {
            private final Product givenProduct = Product.builder()
                    .name("고양이장난감1")
                    .maker("삼성")
                    .price(50000L)
                    .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                    .build();

            @Test
            @DisplayName("DB에 등록하고 등록된 장난감을 리턴한다")
            void it_returns_registered_cat_toy() {
                final Product actualProduct = productRepository.save(givenProduct);

                assertThat(actualProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(actualProduct.getPrice()).isEqualTo(givenProduct.getPrice());
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_existed_id {
            private Product givenProduct;

            @BeforeEach
            void prepare() {
                final Product product = Product.builder()
                        .name("고양이장난감1")
                        .maker("삼성")
                        .price(50000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                givenProduct = productRepository.save(product);
            }

            @Test
            @DisplayName("DB에서 삭제한다")
            void it_returns_nothing() {
                productRepository.delete(givenProduct);

                final Optional<Product> actualProduct = productRepository.findById(givenProduct.getId());

                assertThat(actualProduct).isEmpty();
            }
        }

        @Nested
        @DisplayName("유효하지 않은 ID가 주어지면")
        class Context_with_non_existed_id {
            private Product givenProduct;
            @BeforeEach
            void prepare() {
                givenProduct = Product.builder()
                        .id(100L)
                        .name("고양이장난감1")
                        .maker("삼성")
                        .price(50000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();
            }

            @Test
            @DisplayName("아무것도 하지않는다")
            void it_returns_nothing() {
                final Optional<Product> beforeProduct = productRepository.findById(givenProduct.getId());

                productRepository.delete(givenProduct);

                final Optional<Product> afterProduct = productRepository.findById(givenProduct.getId());

                assertThat(beforeProduct).isEmpty();
                assertThat(afterProduct).isEmpty();
            }
        }


    }
}
