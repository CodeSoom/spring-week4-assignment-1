package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.entity.ProductRepository;
import com.codesoom.assignment.models.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ProductServiceImpl 에서")
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("모든 Product 객체를 불러올 때")
    class Describe_of_readAll_products {

        @Nested
        @DisplayName("Product 객체가 없을 경우")
        class Context_without_product {

            @Test
            @DisplayName("빈 배열을 리턴한다")
            void it_returns_emtpy_list() {
                List<Product> products = productService.getProductList();

                assertThat(products).isEmpty();
            }
        }
    }
}