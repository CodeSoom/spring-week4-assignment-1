package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.application.ProductServiceImpl;
import com.codesoom.assignment.controller.dto.ProductRequestDto;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// TODO: 리팩토링 필요
@DisplayName("ProductController 클래스")
class ProductControllerTest {

    private final String name = "toy";
    private final String maker = "toy shop";
    private final int price = 15000;
    private final String imageUrl = "toy.jpg";

    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productService = mock(ProductServiceImpl.class);
        productController = new ProductController(productService);
    }

    @Test
    void getAll_test() {
        List<Product> productList = List.of(new Product(name, maker, price, imageUrl));
        given(productService.getAll()).willReturn(productList);

        List<Product> products = productController.getAll();

        assertThat(products).hasSize(productList.size());

        verify(productService).getAll();
    }

    @Test
    void getProduct_test() {
        given(productService.get(anyLong())).willReturn(new Product(name, maker, price, imageUrl));

        Product product = productController.getProduct(1L);

        assertThat(product).isNotNull();

        verify(productService).get(anyLong());
    }

    @Test
    void createProduct_test() {
        final ProductRequestDto requestDto = new ProductRequestDto(name, maker, price, imageUrl);
        final Product source = new Product(name, maker, price, imageUrl);

        given(productService.create(any(Product.class))).willReturn(source);

        Product product = productController.createProduct(requestDto);

        assertThat(product).isNotNull();

        verify(productService).create(any(Product.class));
    }

    @Test
    void deleteProduct_test() {
        when(productService.get(1L)).thenReturn(new Product(name, maker, price, imageUrl));

        productController.delete(1L);

        verify(productService).deleteById(1L);
    }

    @Test
    void updateProduct_test() {
        final String updatePrefix = "update_";
        final int updatedPrice = 10000;

        final ProductRequestDto requestDto = new ProductRequestDto(updatePrefix + name,
                updatePrefix + maker,
                updatedPrice,
                updatePrefix + imageUrl);

        final Product product = requestDto.toEntity();

        given(productService.update(eq(1L), any(Product.class))).willReturn(product);

        Product updated = productController.update(1L, requestDto);

        assertThat(updated).isNotNull();
        verify(productService).update(1L, product);
    }
}
