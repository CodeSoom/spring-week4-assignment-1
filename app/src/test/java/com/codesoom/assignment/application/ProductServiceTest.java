package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.codesoom.assignment.domain.Product;
// TODO 테스트
// 1.
class ProductServiceTest {

    private ProductService productService;
    private Product product;
    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);

        product = new Product();
        product.setId(1L);
        product.setName("탱탱볼");
        product.setPrice(5000L);
        product.setImage("/Users/static/image.jpg");
        given(productService.createProduct(product)).willReturn(product);
    }

    @Test
    public void createProduct() {
        Product ret = productService.createProduct(product);
        assertThat(ret.getId()).isEqualTo(1L);
    }
}
