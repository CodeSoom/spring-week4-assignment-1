package com.codesoom.assignment.product.domain;

import com.codesoom.assignment.product.ProductFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductRepository 인터페이스의")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {
        private final Product product = ProductFixtures.laser();

        @Test
        @DisplayName("새로운 상품을 생성한 후 생성된 상품을 반환한다")
        void It_saves_the_product_and_returns_it() {
            Product createdProduct = productRepository.save(product);

            assertThat(product.getId())
                    .isEqualTo(createdProduct.getId());
            assertThat(product.getName())
                    .isEqualTo(createdProduct.getName());
            assertThat(product.getMaker())
                    .isEqualTo(createdProduct.getMaker());
            assertThat(product.getPrice())
                    .isEqualTo(createdProduct.getPrice());
            assertThat(product.getImageUrl())
                    .isEqualTo(createdProduct.getImageUrl());
        }
    }
}
