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

    private static final int PRODUCTS_MAX_SIZE = 5;
    private static final Long VALID_PRODUCT_ID = 1L;
    private static final Long INVALID_PRODUCT_ID = 100L;

//    @InjectMocks
    private ProductController productController;

//    @Mock
    private ProductService productService;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);

        productController = new ProductController(productService);

        products = new ArrayList<>();

        Product product = null;
        for(int i = 0; i < PRODUCTS_MAX_SIZE; i++) {
            product = new Product(TEST_NAME + (i + 1),
                    TEST_MAKER + (i + 1),
                    TEST_PRICE + (i + 1),
                    (i + 1) + TEST_IMAGE_PATH);
            products.add(product);
        }
    }

    @Test
    void create() {
        Product newProduct = new Product(
                TEST_NAME + CREATE_POSTFIX,
                TEST_MAKER + CREATE_POSTFIX,
                TEST_PRICE + 1000L,
                CREATE_POSTFIX + TEST_IMAGE_PATH);

        productController.create(newProduct);

        verify(productService).createProduct(newProduct);
    }

    @Test
    void listWithoutProducts() {
        given(productService.getProducts()).willReturn(new ArrayList<>());

        assertThat(productController.list()).isEmpty();

        verify(productService).getProducts();
    }

    @Test
    void listWithSomeProducts() {
        given(productService.getProducts()).willReturn(products);

        assertThat(productController.list()).isNotEmpty();

        verify(productService).getProducts();
    }

    @Test
    void detailWithExistedId() {
        given(productService.getProduct(VALID_PRODUCT_ID))
                .willReturn(products.get(VALID_PRODUCT_ID.intValue() - 1));

        Product product = productController.detail(VALID_PRODUCT_ID);

        verify(productService).getProduct(VALID_PRODUCT_ID);

        assertThat(product).isNotNull();
    }

    @Test
    void detailWithNotExistedId() {
        given(productService.getProduct(INVALID_PRODUCT_ID))
                .willThrow(new ProductNotFoundException(INVALID_PRODUCT_ID));

        assertThatThrownBy(() -> productController.detail(INVALID_PRODUCT_ID))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productService).getProduct(INVALID_PRODUCT_ID);
    }

    @Test
    void updateExistedId() {
        Product source = new Product(
                TEST_NAME + UPDATE_POSTFIX,
                TEST_MAKER + UPDATE_POSTFIX,
                TEST_PRICE + 1000L,
                UPDATE_POSTFIX + TEST_IMAGE_PATH);

        productController.update(VALID_PRODUCT_ID, source);

        verify(productService).updateProduct(VALID_PRODUCT_ID, source);
    }

    @Test
    void updateNotExistedId() {
        given(productService.updateProduct(eq(INVALID_PRODUCT_ID), any(Product.class)))
                .willThrow(new ProductNotFoundException(INVALID_PRODUCT_ID));

        Product product = new Product(
                TEST_NAME + UPDATE_POSTFIX,
                TEST_MAKER + UPDATE_POSTFIX,
                TEST_PRICE + 1000L,
                UPDATE_POSTFIX + TEST_IMAGE_PATH);

        assertThatThrownBy(() -> productController.update(INVALID_PRODUCT_ID, product))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productService).updateProduct(INVALID_PRODUCT_ID, product);
    }

    @Test
    void deleteExistedId() {
        productController.delete(VALID_PRODUCT_ID);

        verify(productService).deleteProduct(VALID_PRODUCT_ID);
    }

    @Test
    void deleteNotExistedId() {
        given(productService.deleteProduct(INVALID_PRODUCT_ID))
                .willThrow(new ProductNotFoundException(INVALID_PRODUCT_ID));

        assertThatThrownBy(() -> productController.delete(INVALID_PRODUCT_ID))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productService).deleteProduct(INVALID_PRODUCT_ID);
    }
}
