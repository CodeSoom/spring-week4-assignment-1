package com.codesoom.assignment.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;

class ProductControllerTest {

    ProductController productController;
    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @DisplayName("장난감 리스트에 장난감이 아무것도 없을 때, 비어있는 것을 보장하는 테스트입니다.")
    @Test
    void getProductListFromEmptyListTest() {
        List<Product> productList = new ArrayList<>();
        given(productService.getProducts()).willReturn(productList);
        assertThat(productController.getProducts()).isEmpty();
    }

    @DisplayName("장난감 리스트에 장난감이 있을 때, 비어있지 않는 것을 보장하는 테스트입니다.")
    @Test
    void getProductListFromSomeListTest() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        given(productService.getProducts()).willReturn(productList);

        assertThat(productController.getProducts()).isNotEmpty();
    }
}
