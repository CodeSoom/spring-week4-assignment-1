package com.codesoom.assignment.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductControllerTest {

    private static final long ID = 1L;

    private Product product;
    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        product = new Product("name", "maker", 10000L, "imageUrl");
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    @DisplayName("상품을 생성한다")
    void create() {
        productController.create(product);

        verify(productService).createProduct(product);
    }

    @Test
    @DisplayName("모든 상품을 불러온다")
    void getAllProducts() {
        productController.getAll();

        verify(productService).getAllProducts();
    }

    @Test
    @DisplayName("상품 하나를 불러온다")
    void getProduct() {
        productController.get(ID);

        verify(productService).getProduct(ID);
    }

    @Test
    @DisplayName("상품을 수정한다")
    void update() {
        Product source = new Product("name", "maker", 5000L, "imageUrl");

        productController.update(ID, source);

        verify(productService).updateProduct(ID, source);
    }

    @Test
    @DisplayName("상품을 삭제한다")
    void delete() {
        productController.delete(ID);

        verify(productService).deleteProduct(ID);
    }
}
