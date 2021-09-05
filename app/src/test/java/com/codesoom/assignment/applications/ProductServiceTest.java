package com.codesoom.assignment.applications;

import com.codesoom.assignment.domain.Product;

import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductNotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductServiceTest 클래스")
@DataJpaTest
class ProductServiceTest {
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        productService = new ProductService(productRepository);

        product1 = new Product(0L, "toy1", "maker1", 1000L, "toy1.jpg");
        product2 = new Product(1L, "toy2", "maker2", 2000L, "toy2.jpg");
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("save 메소드")
    class Describe_save {

        @Test
        @DisplayName("새로운 product를 반환합니다.")
        void it_return_new_product() {
            Product newProduct = productService.save(product1);
            equalProduct(newProduct, product1);
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
            List<Product> resultProducts = productService.findAll();

            for(int i=0; i<resultProducts.size(); i++) {
                equalProduct(resultProducts.get(i), givenProducts.get(i));
            }
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
                Product givenProduct = productService.save(product1);
                valid_id = givenProduct.getId();
            }

            @Test
            @DisplayName("해당 Id의 product를 반환합니다.")
            void it_return_product() {
                Product resultProduct = productService.findById(valid_id);
                equalProduct(resultProduct, product1);
            }
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private Long invalid_id;

            @BeforeEach
            void setUp() {
                productService.save(product1);
                invalid_id = product2.getId();
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
        @DisplayName("해당되는 id의 product가 있다면")
        class Context_with_valid_id {

            private Long valid_id;

            @BeforeEach
            void setUp() {
                Product givenProduct = productService.save(product1);
                valid_id = givenProduct.getId();
            }

            @Test
            @DisplayName("업데이트된 product를 반환합니다.")
            void it_return_updated_product() {
                Product updatedProduct = productService.update(valid_id, product2);

                equalProduct(updatedProduct, product2);
            }
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private Long invalid_id;

            @BeforeEach
            void setUp() {
                invalid_id = product2.getId();
            }

            @Test
            @DisplayName("ProductNotFoundException을 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.update(invalid_id, product2))
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
            }

            @Test
            @DisplayName("ProductNotFoundException을 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.deleteById(invalid_id))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    void equalProduct(Product product, Product actualProdcut) {
        assertThat(product.getName()).isEqualTo(actualProdcut.getName());
        assertThat(product.getMaker()).isEqualTo(actualProdcut.getMaker());
        assertThat(product.getPrice()).isEqualTo(actualProdcut.getPrice());
        assertThat(product.getImageUrl()).isEqualTo(actualProdcut.getImageUrl());
    }
}