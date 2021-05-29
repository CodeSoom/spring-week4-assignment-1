package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    private final String PRODUCT_NAME = "Product";
    private final String CREATE_PREFIX = "Create";
    private final String UPDATE_PREFIX = "Update";
    private final Long EXISTED_ID = 1L;
    private final Long NOT_EXISTED_ID = -1L;

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Nested
    @DisplayName("getProductList 메소드는")
    class DescribeGetProductList {

        @Nested
        @DisplayName("프로덕트가 있으면")
        class ContextWithExistedProduct {

            @BeforeEach
            void prepareProduct() {
                List<Product> productList = new ArrayList<>();
                Product product = new Product();
                productList.add(product);
                when(productRepository.findAll()).thenReturn(productList);
            }

            @Test
            @DisplayName("프로덕트 목록을 조회 한 뒤 리턴한다")
            void itReturnsProductList() {
                assertThat(productService.getProductList().size()).isNotZero();
                verify(productRepository, atLeastOnce()).findAll();
            }
        }

        @Nested
        @DisplayName("프로덕트가 없으면")
        class ContextWithNotExistedProduct {

            @Test
            @DisplayName("빈 프로덕트 목록을 리턴한다")
            void itReturnsEmptyList() {
                assertThat(productService.getProductList().size()).isZero();
            }
        }
    }

    @Nested
    @DisplayName("getDetailProduct 메소드는")
    class DescribeGetDetailProduct {

        @Nested
        @DisplayName("productId가 존재하면")
        class ContextWithExistedProductId {

            @BeforeEach
            void prepareProduct() {
                Product product = new Product();
                product.setName(PRODUCT_NAME);
                when(productRepository.findById(EXISTED_ID)).thenReturn(Optional.of(product));
            }

            @Test
            @DisplayName("프로덕트 상세정보를 조회한 뒤 리턴한다")
            void itReturnsDetailProduct() {
                Product findProduct = productService.getProduct(EXISTED_ID);
                assertThat(findProduct).isNotNull();
                assertThat(findProduct.getName()).isEqualTo(PRODUCT_NAME);
                verify(productRepository, atLeastOnce()).findById(EXISTED_ID);
            }
        }

        @Nested
        @DisplayName("productId가 존재하지 않으면")
        class ContextWithNotExistedProductId {

            @Test
            @DisplayName("ProductNotFoundException 예외를 리턴한다")
            void itReturnsProductNotFoundException() {
                assertThatThrownBy(() -> productService.getProduct(NOT_EXISTED_ID)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class DescribeCreateProduct {

        @Nested
        @DisplayName("새 프로덕트 이면")
        class ContextWithNewProduct {

            @Test
            @DisplayName("새 프로덕트를 저장하고 리턴한다")
            void itReturnsNewProduct() {
                Product product = new Product();
                product.setName(CREATE_PREFIX + PRODUCT_NAME);
                when(productRepository.save(product)).thenReturn(product);
                Product newProduct = productService.createProduct(product);
                assertThat(newProduct).isNotNull();
                assertThat(newProduct.getName()).isEqualTo(CREATE_PREFIX + PRODUCT_NAME);
                verify(productRepository).save(product);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class DescribeUpdateProduct {

        @Nested
        @DisplayName("productId와 product가 존재하면")
        class ContextWithExistedProductIdAndProductForUpdate {

            @BeforeEach
            void prepareProduct() {
                Product product = new Product();
                product.setName(PRODUCT_NAME);
                when(productRepository.findById(EXISTED_ID)).thenReturn(Optional.of(product));
                when(productRepository.save(product)).thenReturn(product);
            }

            @Test
            @DisplayName("프로덕트를 수정한 뒤 리턴한다")
            void itReturnsUpdatedProduct() {
                Product product = new Product();
                product.setName(UPDATE_PREFIX + PRODUCT_NAME);
                Product updatedProduct = productService.updateProduct(EXISTED_ID, product);
                assertThat(updatedProduct.getName()).isEqualTo(UPDATE_PREFIX + PRODUCT_NAME);
                verify(productRepository, atLeastOnce()).findById(EXISTED_ID);
                verify(productRepository).save(any(Product.class));
            }
        }

        @Nested
        @DisplayName("productId가 존재하지 않으면")
        class ContextWithNotExistedProductId {

            @Test
            @DisplayName("ProductNotFoundException 예외를 리턴한다")
            void itReturnsProductNotFoundException() {
                assertThatThrownBy(() -> productService.getProduct(NOT_EXISTED_ID)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class DescribeDeleteProduct {

        @Nested
        @DisplayName("productId가 존재하면")
        class ContextWithExistedProductId {

            @BeforeEach
            void prepareProduct() {
                Product product = new Product();
                when(productRepository.findById(EXISTED_ID)).thenReturn(Optional.of(product));
                doNothing().when(productRepository).delete(product);
            }

            @Test
            @DisplayName("프로덕트를 찾아 삭제한다")
            void itDeletesProduct() {
                productService.deleteProduct(EXISTED_ID);
                verify(productRepository).delete(any(Product.class));
            }
        }

        @Nested
        @DisplayName("productId가 존재하지 않으면")
        class ContextWithNotExistedProductId {

            @Test
            @DisplayName("ProductNotFoundException 예외를 리턴한다")
            void itReturnsProductNotFoundException() {
                assertThatThrownBy(() -> productService.getProduct(NOT_EXISTED_ID)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
