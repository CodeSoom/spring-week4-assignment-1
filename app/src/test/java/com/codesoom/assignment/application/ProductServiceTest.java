package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private List<Product> products;
    private Product product1;

    @BeforeEach
    void setUp() {

        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        products = new ArrayList<>();
        product1 = Product.builder()
                .id(1L)
                .name("name 1")
                .imageUrl("imageURL 1")
                .maker("brand 1")
                .price(100)
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .name("name 2")
                .imageUrl("imageURL 2")
                .maker("brand 2")
                .price(200)
                .build();

        products.add(product1);
        products.add(product2);

    }

    @Nested
    @DisplayName("getProducts는")
    class Describe_getProducts {

        @Nested
        @DisplayName("저장되어 있는 상품이 있을 경우")
        class Context_with_products {
            @BeforeEach
            void setUp() {
                given(productRepository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("모든 상품을 리턴한다")
            void getProducts() {
                List<Product> products = productService.getProducts();

                verify(productRepository).findAll();
                assertThat(products).hasSize(2);
            }
        }

        @Nested
        @DisplayName("저장되어 있는 상품이  경우")
        class Context_without_products {
            @BeforeEach
            void setUp() {
                given(productRepository.findAll()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("빈 목록을 리턴한다")
            void getProducts() {
                List<Product> products = productService.getProducts();

                verify(productRepository).findAll();
                assertThat(products).isEmpty();
            }
        }
    }



    @Test
    @DisplayName("새로운 상품을 생성한다")
    void createProduct() {
        given(productRepository.save(any(Product.class)))
                .will(invocation -> invocation.<Product>getArgument(0));

        productService.createProduct(product1);

        verify(productRepository).save(any(Product.class));
    }
}
