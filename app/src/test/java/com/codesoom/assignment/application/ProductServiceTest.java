package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("ProductService Test")
class ProductServiceTest {

    private ProductService productService;
    private Long ID = 1L;

    @Nested
    @DisplayName("장난감 목록 조회")
    class list {

        @BeforeEach
        void setUp() {
            productService = new ProductService(new InMemoryProductRepository());

            Product product = new Product("고양이 생선", BigDecimal.valueOf(10000), "(주)애옹이네", "image.png");
            productService.regist(product);
        }

        @Nested
        @DisplayName("장난감 목록을 조회하면")
        class when_list {

            @Test
            @DisplayName("장난감 목록을 반환합니다.")
            void list() {
                assertThat(productService.findAll().size()).isEqualTo(1);
            }
        }
    }

    @Nested
    @DisplayName("장난감 상세 조회")
    class detail {

        @Nested
        @DisplayName("id로 조회하면")
        class when_detail_with_id {

            @Test
            @DisplayName("장난감 상세 정보를 반환합니다.")
            void detail() {
                productService = new ProductService(new InMemoryProductRepository());

                Product product = new Product("고양이 생선", BigDecimal.valueOf(10000), "(주)애옹이네", "image.png");
                productService.regist(product);

                assertThat(productService.findById(ID).equals(product)).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("장난감 등록")
    class regist {

        @Nested
        @DisplayName("product 로 장난감을 등록하면")
        class when_regist_with_product {

            @Test
            @DisplayName("동일한 정보로 장난감이 등록됩니다.")
            void regist() {
                productService = new ProductService(new InMemoryProductRepository());

                Product product = new Product("고양이 생선", BigDecimal.valueOf(10000), "(주)애옹이네", "image.png");
                assertThat(productService.regist(product).equals(product)).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("장난감 수정")
    class modify {

        @Nested
        @DisplayName("product 로 장난감 정보를 수정하면")
        class when_modify_with_id_product {

            @Test
            @DisplayName("동일한 정보로 장난감이 수정됩니다.")
            void modify() {
                productService = new ProductService(new InMemoryProductRepository());

                Product product = new Product("고양이 생선", BigDecimal.valueOf(10000), "(주)애옹이네", "image.png");
                productService.regist(product);
                product.changeName("고양이생선");
                assertThat(productService.modify(product).equals(product)).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("장난감 삭제")
    class delete {

        @Nested
        @DisplayName("id 로 장난감을 삭제하면")
        class when_regist_with_product {

            @Test
            @DisplayName("해당 id의 장난감 정보가 삭제됩니다.")
            void delete() {
                productService = new ProductService(new InMemoryProductRepository());

                Product product = new Product("고양이 생선", BigDecimal.valueOf(10000), "(주)애옹이네", "image.png");
                productService.regist(product);
                productService.delete(ID);
                assertThat(productService.findAll().size()).isEqualTo(0);
            }
        }
    }

}