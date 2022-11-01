package com.codesoom.assignment.products.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Product Repository 유닛 테스트")
class ProductRepositoryTest {
    private static final Long TEST_ID = 1L;
    @Autowired
    private ProductRepository productRepository;

    /**
     * 계층 구조 테스트 특성상 테스트 인스턴스의 생명주기가 공유됩니다.
     * 따라서 매 테스트마다 모든 fixture 데이터 삭제를 진행합니다.
     */
    @AfterEach
    void setUpDeleteFixture() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class findAll_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_없을_때 {
            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_empty_list() {
                assertThat(productRepository.findAll())
                        .isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_있을_때 {
            @BeforeEach
            void setUpCreate() {
                productRepository.save(TOY_1.생성());
            }

            @Test
            @DisplayName("비어있지 않은 리스트를 리턴한다")
            void it_returns_empty_list() {
                List<Product> products = productRepository.findAll();

                assertThat(products).isNotEmpty();
                assertThat(products).hasSize(1);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class findById_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("빈 값을 리턴한다")
            void it_returns_empty_product() {
                assertThat(productRepository.findById(TEST_ID))
                        .isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @BeforeEach
            void setUpCreateFixture() {
                productRepository.save(TOY_1.생성(TEST_ID));
            }

            @Test
            @DisplayName("해당 id의 장난감 정보를 리턴한다")
            void it_returns_product() {
                Optional<Product> product = productRepository.findById(TEST_ID);

                assertThat(product).isNotEmpty();
                assertThat(product.get().getId()).isEqualTo(TEST_ID);
            }
        }
    }
}
