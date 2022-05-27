package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ToyNotFoundException;
import com.codesoom.assignment.repository.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("ProductService Test")
class ProductServiceTest {

    private ProductService productService;
    private final Long ID = 1L;
    private final Long INVALID_ID = 100L;

    private Product registOneProduct() {
        productService = new ProductService(new InMemoryProductRepository());

        Product product = new Product("고양이 생선", BigDecimal.valueOf(10000), "(주)애옹이네", "image.png");
        return productService.register(product);
    }

    @Nested
    @DisplayName("장난감 목록 조회")
    class list {

        @Nested
        @DisplayName("등록된 장난감이 있다면")
        class when_list {

            @Test
            @DisplayName("장난감 목록을 반환합니다.")
            void list() {
                registOneProduct();
                assertThat(productService.findAll().size()).isEqualTo(1);
            }
        }
        
        @Nested
        @DisplayName("등록된 장난감이 없다면")
        class when_list_is_empty {

            @Test
            @DisplayName("빈 목록을 반환합니다.")
            void list() {
                productService = new ProductService(new InMemoryProductRepository());
                assertThat(productService.findAll()).isEqualTo(new ArrayList<>());
            }
        }
    }

    @Nested
    @DisplayName("장난감 상세 조회")
    class detail {

        @Nested
        @DisplayName("id가 존재한다면")
        class when_detail_with_valid_id {

            @Test
            @DisplayName("장난감 상세 정보를 반환합니다.")
            void detail() {
                Product product = registOneProduct();

                assertThat(productService.findById(ID)).isEqualTo(product);
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않는다면")
        class when_detail_with_invalid_id {

            @Test
            @DisplayName("ToyNotFoundException 예외가 발생합니다.")
            void detail() {
                productService = new ProductService(new InMemoryProductRepository());
                assertThatThrownBy(() -> productService.findById(INVALID_ID)).isInstanceOf(ToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("장난감 등록")
    class register {

        @Nested
        @DisplayName("product 로 장난감을 등록하면")
        class when_register_with_product {

            @Test
            @DisplayName("동일한 정보로 장난감이 등록됩니다.")
            void register() {
                productService = new ProductService(new InMemoryProductRepository());

                Product product = new Product("고양이 생선", BigDecimal.valueOf(10000), "(주)애옹이네", "image.png");
                assertThat(productService.register(product)).isEqualTo(product);
            }
        }
    }

    @Nested
    @DisplayName("장난감 수정")
    class modify {

        @Nested
        @DisplayName("id가 존재한다면")
        class when_modify_with_valid_id {

            @Test
            @DisplayName("동일한 정보로 장난감이 수정됩니다.")
            void modify() {
                Product product = registOneProduct();
                product.changeName("고양이생선");
                assertThat(productService.modify(ID, product)).isEqualTo(product);
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않는다면")
        class when_modify_with_invalid_id {

            @Test
            @DisplayName("ToyNotFound 예외가 발생합니다.")
            void modify() {
                productService = new ProductService(new InMemoryProductRepository());
                Product product = Product.creatNewProduct(ID, new Product("고양이 생선", BigDecimal.valueOf(10000), "(주)애옹이네", "image.png"));

                assertThatThrownBy(() -> productService.modify(INVALID_ID, product)).isInstanceOf(ToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("장난감 삭제")
    class delete {

        @Nested
        @DisplayName("id가 존재한다면")
        class when_delete_with_valid_id {

            @Test
            @DisplayName("해당 id의 장난감 정보가 삭제됩니다.")
            void delete() {
                registOneProduct();
                productService.delete(ID);
                assertThat(productService.findAll().size()).isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않는다면")
        class when_delete_with_invalid_id {

            @Test
            @DisplayName("ToyNotFound 예외가 발생합니다.")
            void delete() {
                productService = new ProductService(new InMemoryProductRepository());
                assertThatThrownBy(() -> productService.delete(INVALID_ID)).isInstanceOf(ToyNotFoundException.class);
            }
        }
    }

}