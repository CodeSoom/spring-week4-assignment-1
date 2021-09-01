package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("CatToyService 클래스")
class CatToyServiceTest {
    private CatToyService catToyService;
    private CatToyRepository catToyRepository;

    @BeforeEach
    void setUp() {
        catToyRepository = mock(CatToyRepository.class);
        catToyService = new CatToyService(catToyRepository);
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("등록된 상품이 없다면")
        class Context_no_have_product {
            @Test
            @DisplayName("비어 있는 리스트를 리턴")
            void it_returns_EmptyProducts() {
                List<Product> products = catToyService.getProducts();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("등록된 상품이 있다면")
        class Context_have_product {
            Product product1, product2;

            @BeforeEach
            void PrepareTask() {
                product1 = new Product();
                product2 = new Product();

                List<Product> products = List.of(product1, product2);

                given(catToyRepository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("등록된 상품들을 리턴")
            void it_returns_products() {
                List<Product> products = catToyService.getProducts();

                verify(catToyRepository).findAll();

                assertThat(products.size()).isEqualTo(2);
                assertThat(products).contains(product1, product2);
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {

        @Nested
        @DisplayName("id와 일치하는 상품이 있다면")
        class Context_exist_match_id {
            Product product;
            final Long EXIST_ID = 1L;

            @BeforeEach
            void prepareProduct() {
                product = new Product();

                List<Product> list = new ArrayList<>();

                list.add(product);

                given(catToyRepository.findById(EXIST_ID))
                        .willReturn(java.util.Optional.of(product));
            }

            @Test
            @DisplayName("상품을 리턴")
            void it_returns_product() {
                Product foundProduct = catToyService.getProduct(EXIST_ID);

                verify(catToyRepository).findById(EXIST_ID);

                assertThat(product).isEqualTo(foundProduct);
            }
        }

        @Nested
        @DisplayName("id와 일치하는 상품이 없다면")
        class Context_not_exist_match_id {
            final Long NOT_EXIST_ID = 1L;

            @BeforeEach
            void prepareProduct() {
                given(catToyRepository.findById(NOT_EXIST_ID)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("찾을 수 없다는 예외를 던짐")
            void it_returns_not_found() {
                assertThatThrownBy(() -> catToyService.getProduct(NOT_EXIST_ID))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(catToyRepository).findById(NOT_EXIST_ID);
            }
        }
    }

    @Nested
    @DisplayName("saveProduct 메소드는")
    class Describe_saveProduct {

        @Nested
        @DisplayName("상품 저장에 성공했다면")
        class Context_success_save_product {
            Product product;

            @BeforeEach
            void prepareProduct() {
                product = new Product();

                given(catToyRepository.save(any(Product.class))).willReturn(product);
            }

            @Test
            @DisplayName("저장한 상품을 리턴")
            void it_returns_product() {
                Product savedProduct = catToyService.saveProduct(product);

                verify(catToyRepository).save(savedProduct);

                assertThat(savedProduct).isEqualTo(product);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {

        @Nested
        @DisplayName("id와 일치하는 상품이 없다면")
        class Context_exist_match_id {
            final Long NOT_EXIST_ID = 1L;
            Product product;

            @BeforeEach
            void prepareProduct() {
                product = new Product();
                given(catToyRepository.findById(NOT_EXIST_ID)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("찾을 수 없다는 예외를 던짐")
            void it_returns_not_found() {
                assertThatThrownBy(() -> catToyService.updateProduct(NOT_EXIST_ID, product))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(catToyRepository).findById(NOT_EXIST_ID);
            }
        }

        @Nested
        @DisplayName("id와 일치하는 상품이 있다면")
        class context_exist_match_id {
            final Long EXIST_ID = 1L;
            Product source;

            @BeforeEach
            void prepareProduct() {
                source = new Product();
                source.setName("update name");
                source.setMaker("update maker");
                source.setPrice(999L);
                source.setImageUrl("update url");

                given(catToyRepository.findById(EXIST_ID))
                        .willReturn(Optional.ofNullable(source));
            }

            @Test
            @DisplayName("상품을 수정하여 리턴")
            void it_returns_updated_product() {
                Product product = catToyService.updateProduct(EXIST_ID, source);

                assertThat(product.getName()).isEqualTo(source.getName());
                assertThat(product.getMaker()).isEqualTo(source.getMaker());
                assertThat(product.getPrice()).isEqualTo(source.getPrice());
                assertThat(product.getImageUrl()).isEqualTo(source.getImageUrl());

                verify(catToyRepository).findById(EXIST_ID);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {

        @Nested
        @DisplayName("id와 일치하는 상품이 없다면")
        class Context_not_exist_match_id {
            final Long NOT_EXIST_ID = 1L;

            @BeforeEach
            void prepareProduct() {
                given(catToyRepository.findById(NOT_EXIST_ID)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("찾을 수 없다는 예외를 던짐")
            void it_returns_not_found() {
                assertThatThrownBy(()-> catToyService.deleteProduct(NOT_EXIST_ID))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(catToyRepository).findById(NOT_EXIST_ID);
            }
        }

        @Nested
        @DisplayName("id와 일치하는 상품이 있다면")
        class context_exist_match_id {
            final Long EXIST_ID = 1L;
            @BeforeEach
            void prepareProduct() {
                Product product = new Product();
                given(catToyRepository.findById(EXIST_ID))
                        .willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("상품을 삭제")
            void it_delete_product() {
                catToyService.deleteProduct(EXIST_ID);

                verify(catToyRepository).findById(EXIST_ID);
                verify(catToyRepository).delete(any(Product.class));
            }
        }
    }


}
