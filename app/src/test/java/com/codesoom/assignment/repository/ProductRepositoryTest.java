package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureMockMvc
@DisplayName("ProductRepository 클래스 테스트")
class ProductRepositoryTest {

    @MockBean
    private ProductRepository repository;

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("id가 존재하지 않을 때")
        class Context_without_id {
            @Test
            @DisplayName("상품을 새로 만들어 리턴한다")
            void it_return_new_product() {
                Product product = new Product("춘식이 고구마 장난감", "kakao", 39000L, "");

                repository.save(product);

                assertThat(repository.findById(1L)).isNotNull();
            }
        }

        @Nested
        @DisplayName("id가 존재할 때")
        class Context_with_id {
            @BeforeEach
            void setUp() {

            }

            @Test
            @DisplayName("상품을 업데이트한다")
            void it_update_product() {

            }
        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        @Test
        @DisplayName("모든 Product 리스트를 리턴한다")
        void it_return_products() {

        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
        @Test
        @DisplayName("해당하는 id의 Product를 리턴한다")
        void it_return_product() {

        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_deleteById {
        @Test
        @DisplayName("해당하는 id의 Product를 삭제한다")
        void it_remove_product() {

        }
    }
}
