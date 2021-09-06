package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
            .name("name")
            .maker("maker")
            .price(10000L)
            .imageUrl("...")
            .build();

        productRepository.save(product);
    }

    @AfterEach
    void cleanUp() {
        productRepository.delete(product);
    }

    @Test
    @DisplayName("저장이 올바르게 되었는지 요소를 검사한다")
    void verity_attributes_when_it_saved() {
        assertThat(product.getName()).isEqualTo("name");
        assertThat(product.getMaker()).isEqualTo("maker");
        assertThat(product.getPrice()).isEqualTo(10000L);
        assertThat(product.getImageUrl()).isEqualTo("...");
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {

        @Nested
        @DisplayName("존재하는 아이디일 경우")
        class Context_existId {

            private long existId;

            @BeforeEach
            void setUp() {
                existId = product.getId();
            }

            @Test
            @DisplayName("찾은 상품을 반환한다")
            void it_returns_found_product() {
                Optional<Product> actual = productRepository.findById(existId);

                assertThat(actual.isPresent()).isTrue();
                assertThat(actual.get()).isEqualTo(product);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 아이디일 경우")
        class Context_notExistId {

            private long notExistId;

            @BeforeEach
            void setUp() {
                productRepository.deleteAll();

                notExistId = product.getId();
            }

            @Test
            @DisplayName("빈 값을 반환한다")
            void it_returns_empty() {
                Optional<Product> actual = productRepository.findById(notExistId);

                assertThat(actual).isEqualTo(Optional.empty());
            }
        }
    }

    @Nested
    @DisplayName("existById 메서드는")
    class Describe_existById {

        @Nested
        @DisplayName("id가 존재할 경우")
        class Context_existId {

            private Long existId;

            @BeforeEach
            void setUp() {
                existId = product.getId();
            }

            @Test
            @DisplayName("true를 리턴한다")
            void it_returns_true() {
                boolean hasId = productRepository.existsById(existId);

                assertThat(hasId).isTrue();
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않은 경우")
        class Context_notExistId {

            private Long notExistId;

            @BeforeEach
            void setUp() {
                productRepository.deleteAll();

                notExistId = product.getId();
            }

            @Test
            @DisplayName("false를 리턴한다")
            void it_returns_false() {
                boolean hasId = productRepository.existsById(notExistId);

                assertThat(hasId).isFalse();
            }
        }
    }
}
