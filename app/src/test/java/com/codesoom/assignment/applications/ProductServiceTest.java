package com.codesoom.assignment.applications;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductServiceTest 클래스")
class ProductServiceTest {
    private ProductService productService;

    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {

        product1 = new Product(1L, "toy1", "maker1", 1000L, "toy1.jpg");
        product2 = new Product(2L, "toy2", "maker2", 2000L, "toy2.jpg");

        productRepository = new ProductRepository();
        productService = new ProductService(productRepository);
    }

    @Nested
    @DisplayName("save 메소드")
    class Describe_save {

        @Test
        @DisplayName("새로운 product를 반환합니다.")
        void it_return_new_product() {
            assertThat(productService.save(product1)).isEqualTo(product1);
        }
    }

    @Nested
    @DisplayName("findAll 메소드")
    class Describe_findAll {

        private List<Product> givenProducts;

        @BeforeEach
        void prepare() {
            givenProducts = List.of(product1, product2);
            productService.save(product1);
            productService.save(product2);
        }

        @Test
        @DisplayName("모든 product를 반환합니다.")
        void it_return_all_product() {
            assertThat(productService.findAll()).isEqualTo(givenProducts);
        }
    }

    @Nested
    @DisplayName("findById 메소드")
    class Describe_findById {

        @Nested
        @DisplayName("해당되는 id의 product가 있다면")
        class Context_with_valid_id {

            private Long valid_id;

            @BeforeEach
            void setUp() {
                valid_id = product1.getId();
                productService.save(product1);
            }

            @Test
            @DisplayName("해당 Id의 product를 반환합니다.")
            void it_return_product() {
                assertThat(productService.findById(valid_id)).isEqualTo(product1);
            }
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private Long invalid_id;

            @BeforeEach
            void setUp() {
                invalid_id = product2.getId();
                productService.save(product1);
            }

            @Test
            @DisplayName("ProductNotFoundException을 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.findById(invalid_id))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드")
    class Describe_update {

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private Long invalid_id;

            @BeforeEach
            void setUp() {
                invalid_id = product2.getId();
                productService.save(product1);
            }

            @Test
            @DisplayName("ProductNotFoundException을 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productRepository.update(invalid_id, product2))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드")
    class Describe_deleteById {

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private Long invalid_id;

            @BeforeEach
            void setUp() {
                invalid_id = product2.getId();
                productService.save(product1);
            }

            @Test
            @DisplayName("ProductNotFoundException을 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.deleteById(invalid_id))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}