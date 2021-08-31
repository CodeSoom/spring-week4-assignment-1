package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
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
                final Product product = new Product("title");
                assertNull(product.getId());

                assertThat(productRepository.save(product))
                    .matches(saved ->
                        saved.getId() != null && product.getTitle().equals(saved.getTitle())
                    );
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
                final Product product = new Product("title");
                final Product savedProduct = productRepository.save(product);
                id = savedProduct.getId();
            }

            @Test
            @DisplayName("찾은 Product를 리턴한다.")
            void it_returns_a_find_product() {
                assertThat(productRepository.findById(id))
                    .matches(output -> output.isPresent())
                    .matches(output -> id.equals(output.get().getId()))
                    .matches(output -> "title".equals(output.get().getTitle()));
            }
        }

        @Nested
        @DisplayName("Product를 찾을 수 없으면")
        class Context_findById_fail {
            @Test
            @DisplayName("빈 값을 리턴한다.")
            void it_returns_a_empty_value() {
                assertThat(productRepository.findById(1L))
                    .matches(output -> output.isEmpty());
            }
        }
    }
}
