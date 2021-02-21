package com.codesoom.assignment.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    @DisplayName("상품 서비스에 아무것도 없을 때, 컨트롤러의 상품 조회 메서드는 비어있는 리스트를 반환합니다.")
    @Test
    void getProductListFromEmptyListTest() {
        List<Product> productList = new ArrayList<>();
        given(productService.getProducts()).willReturn(productList);
        assertThat(productController.getProducts()).isEmpty();
    }

    @DisplayName("상품 서비스 리스트에 상품이 있을 때, 컨트롤러의 상품 조회 메서드는 비어있지 않은 리스트를 반환합니다.")
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

    @DisplayName("상품 서비스에 등록되어 있는 특정 상품을 호출하였을 때, 컨트롤러의 상품 조회 메서드는 특정한 상품을 반환합니다.")
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

    @DisplayName("상품 서비스에 존재하지 않는 특정 상품을 호출하였을 때, 컨트롤러의 상품 조회 메서드는 예외를 호출합니다.")
    @Test
    void getProductWithInValidIdTest() {
        given(productService.getProduct(100L)).willThrow(ProductNonExistException.class);

        assertThatThrownBy(() -> productController.getProduct(100L)).isInstanceOf(ProductNonExistException.class);
    }

    @DisplayName("상품 서비스에 새로운 상품을 등록하였을 때, 컨트롤러의 상품 생성 메서드는 생성한 상품을 빈환합니다.")
    @Test
    void createProductTest() {
        Product product = new Product();
        productController.createProduct(product);
        verify(productService).createProduct(product);
    }

    @DisplayName("상품 서비스에 존재하는 상품을 수정하였을 떄, 컨트롤러의 수정 메서드는 수정한 상품을 반환합니다.")
    @Test
    void updateProductTest() {
        Product product = new Product();
        product.setName("로봇트");
        product.setBrand("namco");
        product.setPrice(5000L);
        product.setImage("robot.png");

        productController.updateProduct(product.getId(), product);
        verify(productService).updateProduct(product.getId(), product);
    }

    @DisplayName("상품 서비스에 존재하는 상품을 삭제하였을 때, 컨트롤러의 삭제 메서드는 삭제한 상품을 반환합니다.")
    @Test
    void deleteProductTest() {
        Product product = new Product();
        productController.deleteProduct(product.getId());
        verify(productService).deleteProduct(product.getId());
    }
}
