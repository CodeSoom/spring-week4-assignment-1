package com.codesoom.assignment.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("ProductService 클래스 테스트")
class ProductServiceTest {

    @Autowired
    private ProductServiceImpl productService;

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Test
        @DisplayName("product를 리포지토리에 저장한다")
        void it_save_product() {

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
        @Nested
        @DisplayName("id가 존재할 때")
        class Context_with_validId {
            @Test
            @DisplayName("해당하는 id의 Product를 리턴한다")
            void it_return_product() {

            }
        }

        @Nested
        @DisplayName("id가 존재하지 않을 때")
        class Context_with_invalidId {
            @Test
            @DisplayName("empty를 리턴한다")
            void it_return_empty() {

            }
        }
    }

//    @Nested
//    @DisplayName("update 메소드는")
//    class Context_update {
//
//    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_deleteById {
        @Test
        @DisplayName("id에 해당하는 product를 삭제한다")
        void it_remove_product() {

        }
    }
}
