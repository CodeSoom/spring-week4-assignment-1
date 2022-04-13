package com.codesoom.assignment.services;

import com.codesoom.assignment.contexts.ContextProductController;
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
            @DisplayName("사이즈가 0인 빈 리스트를 반환한다.")
            void it_returns_empty_list () {
                List<Product> products = productService.getProducts();

                assertThat(products.size()).isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("등록된 고양이 물품이 1개 이상 존재하면")
        class Context_exist_product extends ContextProductService {

            @Test
            @DisplayName("사이즈가 0이상이고 등록된 물품 정보가 담긴 리스트를 반환한다. ")
            void it_returns_gt0_list() {

            }
        }
    }


    @Nested
    @DisplayName("getProduct() 매소드는")
    class Describe_getProduct {

        @Nested
        @DisplayName("product id 에 맞는 물품이 존재할때")
        class Context_exist_matched_product {

            @Test
            @DisplayName("id와 일치하는 물품을 반환한다.")
            void it_returns_matched_product() {

            }

        }

        @Nested
        @DisplayName("product id 에 맞는 물품이 존재하지 않을 때")
        class Context_not_exist_matched_product {

            @Test
            @DisplayName("예외를 발생시킨다.")
            void it_throws_not_found_exception() {

            }
        }
    }


    @Nested
    @DisplayName("create() 매소드는")
    class Describe_create {

        @Nested
        @DisplayName("새로운 product 생성 요청이 올 때")
        class Context_valid_input {

            @Test
            @DisplayName("product 을 생성하고 생성된 product 를 반환한다.")
            void it_returns_created_product() {

            }
        }
    }


    @Nested
    @DisplayName("update() 매소드는")
    class Describe_update {

        @Nested
        @DisplayName("id 와 일치하는 물품이 존재할때")
        class Context_exist_matched_product {

            @Test
            @DisplayName("일치하는 물품의 정보를 수정한 뒤 반환한다.")
            void it_returns_updated_product() {

            }
        }

        @Nested
        @DisplayName("id 와 일치하는 물품이 존재하지 않을 때")
        class Context_not_exist_matched_product {

            @Test
            @DisplayName("예외를 발생시킨다.")
            void it_throws_not_found_exception() {

            }
        }
    }


    @Nested
    @DisplayName("delete() 매소드는")
    class Describe_delete {

        @Nested
        @DisplayName("id 와 일치하는 물품이 존재할때")
        class Context_exist_matched_product {

            @Test
            @DisplayName("일치하는 물품을 삭제한 뒤 204를 반환한다.")
            void it_deletes_product_and_returns_204() {

            }
        }

        @Nested
        @DisplayName("id 와 일치하는 물품이 존재하지 않을 때")
        class Context_not_exist_matched_product {

            @Test
            @DisplayName("예외를 발생시킨다.")
            void it_throws_not_found_exception() {

            }
        }

    }

}
