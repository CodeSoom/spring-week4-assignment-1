package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private List<Product> products;

    @BeforeEach
    void setUp() {

        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        products = new ArrayList<>();
        Product product1 = Product.builder()
                .id(1L)
                .name("name 1")
                .image("imageURL 1")
                .maker("brand 1")
                .price(100)
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .name("name 2")
                .image("imageURL 2")
                .maker("brand 2")
                .price(200)
                .build();

        products.add(product1);
        products.add(product2);

    }

    @Test
    @DisplayName("모든 상품을 조회한다")
    void getProducts() {
        given(productRepository.findAll()).willReturn(products);

        List<Product> products = productService.getProducts();

        verify(productRepository).findAll();

        assertThat(products).hasSize(2);
    }


}
