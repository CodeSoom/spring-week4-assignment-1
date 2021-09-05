package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static com.codesoom.assignment.domain.ProductConstant.ID;
import static com.codesoom.assignment.domain.ProductConstant.NAME;
import static com.codesoom.assignment.domain.ProductConstant.MAKER;
import static com.codesoom.assignment.domain.ProductConstant.IMAGE_URL;
import static com.codesoom.assignment.domain.ProductConstant.PRICE;

import com.codesoom.assignment.dto.ProductDto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("ProductRepository 테스트")
public class ProductRepositoryTest {
    private final ProductDto productDto = new ProductDto(NAME, MAKER, IMAGE_URL, PRICE);
    private final Product product = new Product(productDto);
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {
        @Nested
        @DisplayName("Product 개체가 주어지면")
        class Context_with_a_product {
            @Test
            @DisplayName("주어진 개체를 저장하고 리턴한다.")
            void it_save_object_and_returns_a_saved_object() {
                assertThat(productRepository.save(product))
                    .matches(saved -> saved.getId() != null);
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        private Long id;

        @Nested
        @DisplayName("Product를 찾을 수 있으면")
        class Context_findById_success {
            @BeforeEach
            void setUp() {
                final Product savedProduct = productRepository.save(product);
                id = savedProduct.getId();
            }

            @Test
            @DisplayName("찾은 Product를 리턴한다.")
            void it_returns_a_find_product() {
                assertThat(productRepository.findById(id))
                    .matches(output -> output.isPresent())
                    .matches(output -> id.equals(output.get().getId()));
            }
        }

        @Nested
        @DisplayName("Product를 찾을 수 없으면")
        class Context_findById_fail {
            @Test
            @DisplayName("빈 값을 리턴한다.")
            void it_returns_a_empty_value() {
                assertThat(productRepository.findById(ID))
                    .matches(output -> output.isEmpty());
            }
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("저장된 Product가 있다면")
        class Context_product_exist {
            @BeforeEach
            void setUp() {
                productRepository.save(product);
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            void it_returns_a_product_list() {
                assertThat(productRepository.findAll())
                    .extracting(
                        Product::getName, Product::getMaker,
                        Product::getImageUrl, Product::getPrice
                    )
                    .contains(tuple(NAME, MAKER, IMAGE_URL, PRICE));
            }
        }

        @Nested
        @DisplayName("저장된 Product가 없다면")
        class Context_product_empty {
            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_returns_a_empty_list() {
                assertThat(productRepository.findAll())
                    .isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {
        private Long id;

        @BeforeEach
        void setUp() {
            id = productRepository.save(product).getId();
        }

        @AfterEach
        void tearDown() {
            productRepository.deleteAll();
        }

        @Test
        @DisplayName("Product를 삭제한다.")
        void it_deletes_a_product() {
            assertThat(productRepository.findById(id))
                .matches(output -> output.isPresent())
                .matches(output -> id.equals(output.get().getId()));

            productRepository.delete(product);

            assertThat(productRepository.findById(id))
                .matches(output -> output.isEmpty());
        }
    }
}
