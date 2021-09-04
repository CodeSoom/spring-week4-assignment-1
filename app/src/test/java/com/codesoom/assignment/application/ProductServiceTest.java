package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.codesoom.assignment.domain.ProductConstant.ID;
import static com.codesoom.assignment.domain.ProductConstant.NAME;
import static com.codesoom.assignment.domain.ProductConstant.MAKER;
import static com.codesoom.assignment.domain.ProductConstant.IMAGE_URL;
import static com.codesoom.assignment.domain.ProductConstant.PRICE;

import java.util.Optional;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.google.common.collect.Lists;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ProductService 클래스")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private final ProductDto productDto = new ProductDto(NAME, MAKER, IMAGE_URL, PRICE);

    @Nested
    @DisplayName("create 메서드는")
    class Describe_createProduct {
        @BeforeEach
        void setUp() {
            when(productRepository.save(any(Product.class)))
                .thenReturn(new Product(productDto));
        }

        @Test
        @DisplayName("Product를 생성하고 리턴한다.")
        void it_returns_a_product() {
            assertThat(productService.createProduct(new Product(productDto)))
                .isInstanceOf(Product.class);
        }

        @AfterEach
        void tearDown() {
            verify(productRepository)
                .save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("detailProduct 메서드는")
    class Describe_detailProduct {
        @Nested
        @DisplayName("Product를 찾을 수 있다면")
        class Context_find_success {
            @BeforeEach
            void setUp() {
                when(productRepository.findById(anyLong()))
                    .thenReturn(Optional.of(new Product(productDto)));
            }

            @Test
            @DisplayName("찾은 Product를 리턴한다.") void it_returns_a_find_product() {
                assertThat(productService.detailProduct(ID))
                    .isInstanceOf(Product.class);
            }
        }

        @Nested
        @DisplayName("Product를 찾을 수 없다면")
        class Context_find_fail {
            @BeforeEach
            void setUp() {
                when(productRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다.")
            void it_throws_a_productNotFoundException() {
                assertThatThrownBy(() -> productService.detailProduct(ID))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @AfterEach
        void tearDown() {
            verify(productRepository)
                .findById(any(Long.class));
        }
    }

    @Nested
    @DisplayName("listProduct 메서드는")
    class Describe_listProduct {
        @Nested
        @DisplayName("저장된 Product가 있다면")
        class Context_product_exist {
            @BeforeEach
            void setUp() {
                when(productRepository.findAll())
                    .thenReturn(Lists.newArrayList(new Product(productDto)));
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            void it_returns_a_product_list() {
                assertThat(productService.listProduct())
                    .isNotEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 Product가 없다면")
        class Context_product_empty {
            @BeforeEach
            void setUp() {
                when(productRepository.findAll())
                    .thenReturn(Lists.newArrayList());
            }

            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_returns_a_empty_list() {
                assertThat(productService.listProduct())
                    .isEmpty();
            }
        }

        @AfterEach
        void tearDown() {
            verify(productRepository).findAll();
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_updateProduct {
        @Nested
        @DisplayName("Product를 찾을 수 있다면")
        class Context_find_success {
            @Mock
            private Product product;

            @BeforeEach
            void setUp() {
                final Long UPDATED_PRICE = PRICE.longValue() + PRICE.longValue();
                final ProductDto updateProductDto = new ProductDto(
                    "updated" + NAME, "updated" + MAKER,
                    "updated" + IMAGE_URL, UPDATED_PRICE
                );
                when(product.update(any(Product.class)))
                    .thenReturn(new Product(updateProductDto));
                when(productRepository.findById(anyLong()))
                    .thenReturn(Optional.of(product));
            }

            @Test
            @DisplayName("업데이트한 Product를 리턴한다.")
            void it_returns_a_updated_product() {
                assertThat(productService.updateProduct(ID, new Product(productDto)))
                    .isInstanceOf(Product.class);
            }

            @AfterEach
            void tearDown() {
                verify(product).update(any(Product.class));
            }
        }

        @Nested
        @DisplayName("Product를 찾을 수 없다면")
        class Context_find_fail {
            @BeforeEach
            void setUp() {
                when(productRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다.")
            void it_throws_a_productNotFoundException() {
                assertThatThrownBy(() -> productService.updateProduct(ID, new Product(productDto)))
                    .isInstanceOf(ProductNotFoundException.class);

            }
        }

        @AfterEach
        void tearDown() {
            verify(productRepository)
                .findById(anyLong());
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("Product를 찾을 수 없다면")
        class Context_find_fail {
            @BeforeEach
            void setUp() {
                when(productRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());
            }

            @Test
            @DisplayName("ProductNotFoundExceptio을 리턴한다.")
            void it_throws_a_productNotFoundException() {
                assertThatThrownBy(() -> productService.deleteProduct(ID))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("Product를 찾을 수 있다면")
        class Context_find_success {
            @BeforeEach
            void setUp() {
                when(productRepository.findById(anyLong()))
                    .thenReturn(Optional.of(new Product(productDto)));
            }

            @Test
            @DisplayName("Product를 삭제한다.")
            void it_deletes_a_product() {
                productService.deleteProduct(ID);
            }

            @AfterEach
            void tearDown() {
                verify(productRepository)
                    .delete(any(Product.class));
            }
        }

        @AfterEach
        void tearDown() {
            verify(productRepository).findById(anyLong());
        }
    }
}
