package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Nested
    @DisplayName("save 메서드")
    class Describe_save {
        @BeforeEach
        void setUp() {
            productRepository.deleteAll();
        }

        @Nested
        @DisplayName("Product 개체가 주어지면")
        class Context_with_a_member {
            private final Product product = new Product("title");

            @Test
            @DisplayName("주어진 개체를 저장하고 리턴한다.")
            void it_save_object_and_returns_a_saved_object() {
                assertNull(product.getId());

                assertThat(productRepository.save(product))
                    .matches(saved ->
                        saved.getId() != null && product.getTitle().equals(saved.getTitle())
                    );
            }
        }
    }
}
