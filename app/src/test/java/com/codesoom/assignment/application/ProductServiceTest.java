package com.codesoom.assignment.application;


import com.codesoom.assignment.BaseProductTest;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest extends BaseProductTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepositoryMock;


    @Nested
    @DisplayName("createProduct 메소드는")
    class Discribe_createProduct {
        @Test
        @DisplayName("Product 를 생성하고 리턴한다")
        void it_create_return_product() {
            Product product = supplyDummyProduct();
            Product created = productService.createProduct(product);

            assertThat(created).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(product);
        }
    }


    @Nested
    @DisplayName("detailProduct 메소드는")
    class Discribe_detailProduct {
        @Nested
        @DisplayName("프러덕트를 찾을 수 있다면")
        class Context_can_find_product {
            @BeforeEach
            void setUp() {
                when(productRepositoryMock.findById(anyLong()))
                        .thenReturn(Optional.of(supplyDummyProduct()));
            }

            @Test
            @DisplayName("찾은 프러덕트를 리턴한다")
            void it_returns_found_product() {
                assertThat(productService.detailProduct(PRODUCT_ID)).isInstanceOf(Product.class);
            }
        }

        @Nested
        @DisplayName("프러덕트를 찾을 수 없다면")
        class Context_cannot_find_product {
        }
    }


    @Nested
    @DisplayName("listProduct 메소드는")
    class Discribe_listProduct {
        @Nested
        @DisplayName("저장된 프러덕트가 있다면")
        class Context_can_find_product {
            @BeforeEach
            void setUp() {
                when(productRepositoryMock.findAll())
                        .thenReturn(Lists.newArrayList(supplyDummyProduct()));
            }

            @Test
            @DisplayName("프러덕트의 리스트를 리턴한다")
            void it_returns_product_list() {
                assertThat(productService.listProduct())
                        .isNotEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 프러덕트가 없다면")
        class Context_cannot_find_product {
            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_product_list() {
                assertThat(productService.listProduct())
                        .isEmpty();
            }
        }
    }


    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Discribe_deleteProduct {
        @Nested
        @DisplayName("프러덕트를 찾을 수 있다면")
        class Context_can_find_product {
        }

        @Nested
        @DisplayName("프러덕트를 찾을 수 없다면")
        class Context_cannot_find_product {
        }

    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Discribe_updateProduct {
        @Nested
        @DisplayName("프러덕트를 찾을 수 있다면")
        class Context_can_find_product {
            @Test
            @DisplayName("멤버 변수들을 업데이트한다")
            void it_updates_member_variable() {
                Product newProduct = new Product();
                Long id = PRODUCT_ID;

                Product updateProduct = productService.updateProduct(id, newProduct);

                assertThat(updateProduct).usingRecursiveComparison().isEqualTo(newProduct);
            }
        }

        @Nested
        @DisplayName("프러덕트를 찾을 수 없다면")
        class Context_cannot_find_product {
        }
    }


}
