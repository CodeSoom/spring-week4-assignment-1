package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductControllerTest {

    private ProductController productController;
    private ProductService productService;

    private final String PRODUCT_NAME = "dyson";
    private final String PRODUCT_MAKER = "nike";
    private final int PRODUCT_PRICE = 5000;
    private final String PRODUCT_IMAGE = "dyson-kodolth.png";

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);

        List<Product> products = new ArrayList<>();
        Product product =  Product.builder()
                                    .name(PRODUCT_NAME)
                                    .maker(PRODUCT_MAKER)
                                    .price(PRODUCT_PRICE)
                                    .image(PRODUCT_IMAGE)
                                    .build();
        products.add(product);

        given(productService.findAll()).willReturn(products);

        given(productService.find(1L)).willReturn(product);

        given(productService.find(100L))
                .willThrow(new ProductNotFoundException(100L));
    }

    @Test
    @DisplayName("제품 목록 전체를 조회하고 목록이 비어있는지 확인한다.")
    void findAllWithoutProducts() {
        given(productService.findAll()).willReturn(new ArrayList<>()); // 비어있는 객체를 return해줌

        assertThat(productController.findAll()).isEmpty();

        verify(productService).findAll();
    }

    @Test
    @DisplayName("제품 목록 전체를 조회하고 목록이 존재하는지 확인한다.")
    void findAllWithSomeProducts() {
        assertThat(productController.findAll()).isNotEmpty();

        verify(productService).findAll();
    }

    @Test
    @DisplayName("특정 제품 목록을 조회하고 목록값을 확인한다.")
    void findOneWithExistedId() {
        Product product = productController.find(1L);

        assertThat(product).isNotNull();

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
        assertThat(product.getImage()).isEqualTo(PRODUCT_IMAGE);
    }

    @Test
    @DisplayName("특정 제품 목록을 조회하고 존재하지 않으면 예외를 던진다.")
    void findOneWithNotExistedId() {
        assertThatThrownBy(() -> productService.find(100L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("새로운 제품 목록을 추가한다.")
    void create() {
//        Product product = new Product();
//        product.setMaker("adidas");
//        productController.create(product);

//        verify(productService).create(product);
    }
}
