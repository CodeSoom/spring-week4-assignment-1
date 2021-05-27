package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import com.codesoom.assignment.infra.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductService 클래스")
class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
    }

    @Nested
    @DisplayName("getAllProducts 메소드는")
    class Describe_of_getAllProducts {

        @Nested
        @DisplayName("등록된 상품이 없을 때")
        class Context_of_empty_products {

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void it_returns_empty_list() {
                assertThat(productService.getAllProducts())
                        .isEmpty();
            }
        }

        @Nested
        @DisplayName("등록된 상품이 있을 때")
        class Context_of_not_empty_products {

            private List<ServiceEntry> serviceEntryList = new ArrayList<>();

            @BeforeEach
            void setServiceEntryListWithDifferentSize() {
                ArrayList<Integer> sizeCases = new ArrayList<>(Arrays.asList(1, 2, 100, 1024));

                sizeCases.forEach(size -> serviceEntryList.add(new ServiceEntry(size, generateProductService(size))));
            }

            @Test
            @DisplayName("모든 상품 리스트를 반환한다")
            void it_returns_empty_list() {
                serviceEntryList.forEach(entry ->
                    assertThat(entry.getProductService().getAllProducts())
                        .hasSize(entry.getSize())
                );
            }

            private class ServiceEntry {

                private Integer size;
                private ProductService productService;

                ServiceEntry(Integer size, ProductService productService) {
                    this.size = size;
                    this.productService = productService;
                }

                public Integer getSize() {
                    return size;
                }

                public ProductService getProductService() {
                    return productService;
                }
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_of_getProduct {

        @Nested
        @DisplayName("상품이 등록되어 있을 때")
        class Context_of_product_registerd {

            private Product registedProduct;

            @BeforeEach
            void setUp() {
                registedProduct = generateProduct(1L);
                productService.addProduct(registedProduct);
            }

            @Nested
            @DisplayName("존재하지 않는 id가 주어지면")
            class Context_of_non_existent_id {

                private Product notRegisteredProduct;
                private Long nonExistentId;

                @BeforeEach
                void setUp() {
                    notRegisteredProduct = generateProduct(-1L);
                    nonExistentId = notRegisteredProduct.getId();
                }

                @Test
                @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
                void it_throws_exception() {
                    assertThatThrownBy(() -> productService.getProduct(nonExistentId))
                            .isInstanceOf(ProductNotFoundException.class);
                }
            }

            @Nested
            @DisplayName("존재하는 id가 주어지면")
            class Context_of_existent_id {

                private Long existentId;

                @BeforeEach
                void setUp() {
                    existentId = registedProduct.getId();
                }

                @Test
                @DisplayName("상품을 반환한다")
                void it_returns_product() {
                    assertThat(productService.getProduct(existentId))
                            .isEqualTo(registedProduct);
                }
            }
        }
    }

    @Nested
    @DisplayName("addProduct 메소드는")
    class Describe_of_addProduct {

        @Nested
        @DisplayName("상품이 주어지면")
        class Context_of_givenProduct {

            private Product givenProduct;

            @BeforeEach
            void setUp() {
                givenProduct = generateProduct(1L);
            }

            @Test
            @DisplayName("상품을 추가하고, 추가한 상품을 반환한다")
            void it_returns_a_product() {
                assertThat(productService.addProduct(givenProduct))
                        .isEqualTo(givenProduct)
                        .withFailMessage("추가한 상품이 반환되지 않았다");

                assertThat(productService.getProduct(givenProduct.getId()))
                        .isEqualTo(givenProduct)
                        .withFailMessage("상품이 추가되지 않았다");
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_of_updateProduct {

        @Nested
        @DisplayName("상품이 등록되어 있을 때")
        class Context_of_registered_product {

            private Product registeredProduct;

            @BeforeEach
            void setUp() {
                registeredProduct = generateProduct(1L);
                productService.addProduct(registeredProduct);
            }

            @Nested
            @DisplayName("존재하는 id와 상품 정보가 주어지면")
            class Context_of_existent_id_and_product {

                private Long existentId;
                private Product givenProduct;

                @BeforeEach
                void setUp() {
                    existentId = registeredProduct.getId();
                    givenProduct = generateProduct(42L);
                    givenProduct.setId(registeredProduct.getId());
                }

                @Test
                @DisplayName("상품을 갱신하고, 갱신한 상품을 반환한다")
                void it_updates_product_and_returns_it() {
                    assertThat(productService.updateProduct(existentId, givenProduct))
                            .isEqualTo(givenProduct)
                            .withFailMessage("갱신한 상품을 반환하지 않았다");

                    assertThat(productService.getProduct(existentId))
                            .isEqualTo(givenProduct)
                            .withFailMessage("상품이 갱신되지 않았다");
                }
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_of_deleteProduct {

        @Nested
        @DisplayName("상품이 등록되어 있을 때")
        class Context_of_registered_product {

            private Product registeredProduct;

            @BeforeEach
            void setup() {
                registeredProduct = generateProduct(1L);
                productService.addProduct(registeredProduct);
            }

            @Nested
            @DisplayName("존재하지 않는 상품의 id가 주어지면")
            class Context_of_non_existent_id {

                private Product nonExistentProduct;
                private Long nonExistentId;

                @BeforeEach
                void setUp() {
                    nonExistentProduct = generateProduct(-1L);
                    nonExistentId = nonExistentProduct.getId();
                }

                @Test
                @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
                void it_throws_exception() {
                    assertThatThrownBy(() -> productService.deleteProduct(nonExistentId))
                            .isInstanceOf(ProductNotFoundException.class);
                }
            }

            @Nested
            @DisplayName("존재하는 상품의 id가 주어지면")
            class Context_of_existent_id {

                private Long existentId;

                @BeforeEach
                void setUp() {
                    existentId = registeredProduct.getId();
                }

                @Test
                @DisplayName("상품을 제거한다")
                void it_removes_product() {
                    productService.deleteProduct(existentId);

                    assertThatThrownBy(() -> productService.getProduct(existentId))
                            .isInstanceOf(ProductNotFoundException.class)
                            .withFailMessage("상품이 제거되지 않았다");
                }
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

    private ProductService generateProductService(Integer size) {
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(productRepository);

        size += 1;
        for (int i = 1; i < size; i++) {
           productService.addProduct(generateProduct((long)i));
        }
        return productService;
    }
}
