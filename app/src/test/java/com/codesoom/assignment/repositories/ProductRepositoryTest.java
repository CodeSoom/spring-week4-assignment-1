package com.codesoom.assignment.repositories;

import com.codesoom.assignment.domains.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("ProductRepositoryTest 는")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("findAll() 매소드는")
    class Describe_findAll {

        @Nested
        @DisplayName("고양이 장난감 물품이 존재하지 않을 때")
        class Context_empty_product {

            @Test
            @DisplayName("사이즈가 0인 빈 물품 리스트를 반환한다.")
            void it_returns_empty_list() {
                List<Product> results = productRepository.findAll();

                assertThat(results.size()).isEqualTo(0);
            }
        }
    }


}
