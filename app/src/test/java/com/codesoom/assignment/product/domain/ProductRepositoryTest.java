package com.codesoom.assignment.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        @Nested
        @DisplayName("만약 1개의 상품이 주어진다면")
        class Context_with_one_product {
            // given
            private final String name = "cat1";
            private final String maker = "codesoom";
            private final Long price = 33_000L;
            private final Product product = new Product(name, maker, price);

            @Test
            @DisplayName("새로운 상품을 생성한 후 생성된 상품을 반환한다")
            void It_saves_the_product_and_returns_it() {
                Product createdProduct = productRepository.save(product);

                assertEquals(name, createdProduct.getName());
                assertEquals(maker, createdProduct.getMaker());
                assertEquals(price, createdProduct.getPrice());
                assertNull(createdProduct.getImageUrl());
            }
        }
    }
}
