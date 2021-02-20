package com.codesoom.assignment.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNonExistException;

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

    @DisplayName("존재하는 장난감 ID를 호출하였을 떄, 유효한 장난감을 반환하는 것을 보장하는 테스트")
    @Test
    void getProductWithValidIdTest() {
        Product product1 = new Product();
        product1.setName("탱탱볼");
        product1.setBrand("토이저러스");
        product1.setPrice(500L);
        product1.setImage("rubber_ball.png");
        Product product2 = new Product();
        Product product3 = new Product();

        given(productService.getProduct(1L)).willReturn(product1);
        given(productService.getProduct(2L)).willReturn(product2);
        given(productService.getProduct(3L)).willReturn(product3);

        assertThat(productController.getProduct(1L)).isEqualTo(product1);
        assertThat(productController.getProduct(2L)).isEqualTo(product2);
        assertThat(productController.getProduct(3L)).isEqualTo(product3);

        assertThat(productController.getProduct(1L).getName()).isEqualTo("탱탱볼");
        assertThat(productController.getProduct(1L).getBrand()).isEqualTo("토이저러스");
        assertThat(productController.getProduct(1L).getPrice()).isEqualTo(500L);
        assertThat(productController.getProduct(1L).getImage()).isEqualTo("rubber_ball.png");
    }

    @DisplayName("존재하지 않는 장난감 ID를 호출하였을 떄, 장난감이 존재하지 않는 예외를 호출하는 것을 보장하는 테스트")
    @Test
    void getProductWithInValidIdTest() {
        given(productService.getProduct(100L)).willThrow(ProductNonExistException.class);

        assertThatThrownBy(() -> productController.getProduct(100L)).isInstanceOf(ProductNonExistException.class);
    }
}
