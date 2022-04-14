package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

//@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    private static final String TEST_NAME = "testName";
    private static final String TEST_MAKER = "testMaker";
    private static final Long TEST_PRICE = 5000L;
    private static final String TEST_IMAGE_PATH = "testImagePath.jpg";
    private static final String CREATE_POSTFIX = "...";
    private static final String UPDATE_POSTFIX = "!!!";

//    @InjectMocks
    private ProductController productController;

//    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);

        List<Product> products = new ArrayList<>();
        Product product = new Product(
                TEST_NAME, TEST_MAKER, TEST_PRICE, TEST_IMAGE_PATH);
        products.add(product);

        given(productService.getProducts()).willReturn(products);

        given(productService.getProduct(1L)).willReturn(product);

        given(productService.getProduct(100L))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.updateProduct(eq(100L), any(Product.class)))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.deleteProduct(100L))
                .willThrow(new ProductNotFoundException(100L));

        productController = new ProductController(productService);
    }

    @Test
    void create() {
        Product product = new Product(
                TEST_NAME + CREATE_POSTFIX,
                TEST_MAKER + CREATE_POSTFIX,
                TEST_PRICE + 1000L,
                CREATE_POSTFIX + TEST_IMAGE_PATH);

        productController.create(product);

       verify(productService).createProduct(product);
    }

    @Test
    void listWithoutProducts() {
        given(productService.getProducts()).willReturn(new ArrayList<>());

        assertThat(productController.list()).isEmpty();

        verify(productService).getProducts();
    }

    @Test
    void listWithSomeProducts() {
        assertThat(productController.list()).isNotEmpty();

        verify(productService).getProducts();
    }

    @Test
    void detailWithExistedId() {
        Product product = productController.detail(1L);

        verify(productService).getProduct(1L);

        assertThat(product).isNotNull();
    }

    @Test
    void detailWithNotExistedId() {
        assertThatThrownBy(() -> productController.detail(100L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productService).getProduct(100L);
    }

    @Test
    void updateExistedId() {
        Product product = new Product(
                TEST_NAME + UPDATE_POSTFIX,
                TEST_MAKER + UPDATE_POSTFIX,
                TEST_PRICE + 1000L,
                UPDATE_POSTFIX + TEST_IMAGE_PATH);

        productController.update(1L, product);

        verify(productService).updateProduct(1L, product);
    }

    @Test
    void updateNotExistedId() {
        Product product = new Product(
                TEST_NAME + UPDATE_POSTFIX,
                TEST_MAKER + UPDATE_POSTFIX,
                TEST_PRICE + 1000L,
                UPDATE_POSTFIX + TEST_IMAGE_PATH);

        assertThatThrownBy(() -> productController.update(100L, product))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productService).updateProduct(100L, product);
    }

    @Test
    void deleteExistedId() {
        productController.delete(1L);

        verify(productService).deleteProduct(1L);
    }

    @Test
    void deleteNotExistedId() {
        assertThatThrownBy(() -> productController.delete(100L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productService).deleteProduct(100L);
    }
}
