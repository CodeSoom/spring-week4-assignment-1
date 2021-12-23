package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.errors.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    private static final String PRODUCT_NAME = "test";
    private static final String SAVE_POSTFIX = "save";
    private static final String UPDATE_POSTFIX = "update";
    private static final Long productId = 1L;
    private static final Long wrongId = 100L;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        Product product = new Product(productId, PRODUCT_NAME, null, 0, null);

        List<Product> products = new ArrayList<>();
        products.add(product);

        given(productRepository.findAll()).willReturn(products);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(productRepository.findById(eq(wrongId))).willThrow(ProductNotFoundException.class);
    }

    @DisplayName("getProducts는 저장하고 있는 상품 목록을 반환한다")
    @Test
    void getProducts() {
        List<Product> products = productService.getProducts();

        verify(productRepository).findAll();

        assertThat(products).hasSize(1);
    }

    @DisplayName("getProduct는 주어진 아이디의 상품을 반환한다")
    @Test
    void getProduct_ok() {
        Product product = productService.getProduct(productId);

        verify(productRepository).findById(productId);

        assertThat(product.getId()).isEqualTo(productId);
    }

    @DisplayName("getProduct는 상품 목록에서 일치하지 않는 아이디이면 에러를 던진다")
    @Test
    void getProduct_no() {
        assertThatThrownBy(() -> productService.getProduct(wrongId))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("saveProduct는 주어진 상품을 저장한다")
    @Test
    void saveProduct() {
        given(productRepository.save(any(Product.class)))
                .will(invocation -> invocation.getArgument(0));

        String productName = PRODUCT_NAME + SAVE_POSTFIX;
        Product source = Product.createSaveProduct(productName, null, 0, null);

        Product product = productService.saveProduct(source);

        verify(productRepository).save(source);

        assertThat(product.getName()).isEqualTo(productName);
    }

    @DisplayName("updateProduct는 주어진 아이디의 상품을 수정한다")
    @Test
    void updateProduct_ok() {
        String productName = PRODUCT_NAME + UPDATE_POSTFIX;
        Product source = new Product(null, productName, null, 0, null);

        Product product = productService.updateProduct(productId, source);

        assertThat(product.getName()).isEqualTo(productName);
    }

    @DisplayName("updateProduct는 상품 목록에 일치하지 않는 아이디이면 에러를 던진다")
    @Test
    void updateProduct_no() {
        String productName = PRODUCT_NAME + UPDATE_POSTFIX;
        Product source = new Product(null, productName, null, 0, null);

        assertThatThrownBy(() -> productService.updateProduct(wrongId, source))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("deleteProduct는 주어진 아이디의 상품을 삭제한다")
    @Test
    void deleteProduct_ok() {
        productService.deleteProduct(productId);

        verify(productRepository).delete(any(Product.class));
    }

    @DisplayName("deleteProduct는 상품 목록에 일치하지 않는 아이디이면 에러를 던진다")
    @Test
    void deleteProduct_no() {
        assertThatThrownBy(() -> productService.deleteProduct(wrongId))
                .isInstanceOf(ProductNotFoundException.class);
    }

}