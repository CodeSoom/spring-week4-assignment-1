package com.codesoom.assignment.services;

import com.codesoom.assignment.contexts.ContextProductService;
import com.codesoom.assignment.domains.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductServiceTest의 ")
class ProductServiceTest {

    @Nested
    @DisplayName("getProducts() 매소드는 ")
    class Describe_getProducts {

        @Nested
        @DisplayName("가게에 등록된 상품이 없을 때")
        class Context_no_exist_product extends ContextProductService {

            @Test
            @DisplayName("빈 상품 리스트를 반환합니다.")
            void it_returns_empty_list () {
                List<Product> products = productService.getProducts();

                assertThat(products.size()).isEqualTo(0);
            }
        }
    }

}
