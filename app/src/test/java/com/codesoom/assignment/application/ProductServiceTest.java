package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductService 클래스")
class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    private Product registeredProduct;
    final private Long EXISTENT_ID = 1L;
    final private Long NON_EXISTENT_ID = -1L;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
        setUpFixture();
    }

    void setUpFixture() {
        registeredProduct = generateProduct(EXISTENT_ID);

        given(productRepository.findById(EXISTENT_ID))
                .willReturn(Optional.ofNullable(registeredProduct));

        willThrow(new EmptyResultDataAccessException(Math.toIntExact(NON_EXISTENT_ID)))
                .given(productRepository).deleteById(NON_EXISTENT_ID);
    }

    @Nested
    @DisplayName("getAllProducts 메소드는")
    class Describe_of_getAllProducts {

        @Nested
        @DisplayName("등록된 상품이 없을 때")
        class Context_of_empty_products {

            @BeforeEach
            void setUp() {
                given(productRepository.findAll())
                        .willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void it_returns_empty_list() {
                assertThat(productService.getAllProducts())
                        .isEmpty();

                verify(productRepository).findAll();
            }
        }

        @Nested
        @DisplayName("등록된 상품이 있을 때")
        class Context_of_not_empty_products {

            @BeforeEach
            void setUp() {
                List<Product> products = new ArrayList<>();
                products.add(registeredProduct);

                given(productRepository.findAll())
                        .willReturn(products);
            }

            @Test
            @DisplayName("모든 상품 리스트를 반환한다")
            void it_returns_all_products() {
                List<Product> products = productService.getAllProducts();

                verify(productRepository).findAll();

                assertThat(products.get(0).getName())
                        .isEqualTo(registeredProduct.getName());
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_of_getProduct {

        @Nested
        @DisplayName("존재하지 않는 id가 주어지면")
        class Context_of_non_existent_id {

            private Long givenId;

            @BeforeEach
            void setUp() {
                givenId = NON_EXISTENT_ID;
            }

            @Test
            @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.getProduct(givenId))
                        .isInstanceOf(EmptyResultDataAccessException.class);
            }
        }

        @Nested
        @DisplayName("존재하는 id가 주어지면")
        class Context_of_existent_id {

            private Long givenId;

            @BeforeEach
            void setUp() {
                givenId = EXISTENT_ID;
            }

            @Test
            @DisplayName("상품을 반환한다")
            void it_returns_product() {
                assertThat(productService.getProduct(givenId))
                        .isEqualTo(registeredProduct);

                verify(productRepository).findById(givenId);
            }
        }
    }

    @Nested
    @DisplayName("addProduct 메소드는")
    class Describe_of_addProduct {

        @BeforeEach
        void setUp() {
            given(productRepository.save(any(Product.class)))
                    .will(invocation -> invocation.getArgument(0));
        }

        @Nested
        @DisplayName("상품이 주어지면")
        class Context_of_givenProduct {

            private Product givenProduct;

            @BeforeEach
            void setUp() {
                givenProduct = generateProduct(2L);
            }

            @Test
            @DisplayName("상품을 추가하고, 추가한 상품을 반환한다")
            void it_returns_a_product() {
                assertThat(productService.addProduct(givenProduct))
                        .isEqualTo(givenProduct)
                        .withFailMessage("추가한 상품이 반환되지 않았다");

                verify(productRepository).save(givenProduct);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_of_updateProduct {

        @BeforeEach
        void setUp() {
            given(productRepository.save(any(Product.class)))
                    .will(invocation -> invocation.getArgument(0));
        }

        @Nested
        @DisplayName("존재하는 id와 갱신할 상품내용이 주어지면")
        class Context_of_existent_id_and_product {

            private Long givenId;
            private Product givenProduct;
            private Product result;

            @BeforeEach
            void setUp() {
                givenId = EXISTENT_ID;

                givenProduct = generateProduct(42L);

                result = new Product();
                result.updateBy(givenProduct);
                result.setId(EXISTENT_ID);
            }

            @Test
            @DisplayName("상품을 갱신하고, 갱신한 상품을 반환한다")
            void it_updates_product_and_returns_it() {
                assertThat(productService.updateProduct(givenId, givenProduct))
                        .isEqualTo(result)
                        .withFailMessage("갱신한 상품을 반환하지 않았다");

                verify(productRepository).findById(givenId);
                verify(productRepository).save(result);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_of_deleteProduct {

        @Nested
        @DisplayName("존재하지 않는 상품의 id가 주어지면")
        class Context_of_non_existent_id {

            private Long givenId;

            @BeforeEach
            void setUp() {
                givenId = NON_EXISTENT_ID;
            }

            @Test
            @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.deleteProduct(givenId))
                        .isInstanceOf(EmptyResultDataAccessException.class);
            }
        }

        @Nested
        @DisplayName("존재하는 상품의 id가 주어지면")
        class Context_of_existent_id {

            private Long givenId;

            @BeforeEach
            void setUp() {
                givenId = EXISTENT_ID;
            }

            @Test
            @DisplayName("상품을 제거한다")
            void it_removes_product() {
                productService.deleteProduct(givenId);

                verify(productRepository).deleteById(givenId);
            }
        }
    }

    private Product generateProduct(Long id) {
        Product newProduct = new Product();
        newProduct.setId(id);
        newProduct.setName("product" + id);
        newProduct.setPrice(id * 100);
        newProduct.setMaker("maker" + id);
        newProduct.setImageUrl(String.format("product%d.jpg", id));
        return newProduct;
    }
}
