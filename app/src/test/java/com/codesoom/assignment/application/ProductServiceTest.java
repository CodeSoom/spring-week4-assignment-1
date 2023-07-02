package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.out.ProductPort;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.adapter.out.persistence.ProductSpringDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    ProductService productService;
    ProductPort productPort;

    private static final String PRODUCT_NAME = "test name";
    private static final String PRODUCT_MAKER = "test maker";
    private static final int PRODUCT_PRICE = 1000;
    private static final String PRODUCT_IMAGE_URL = "test image url";

    @BeforeEach
    void setup() {
        productPort = mock(ProductPort.class);
        productService = new ProductService(productPort);

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName(PRODUCT_NAME);
        product.setMaker(PRODUCT_MAKER);
        product.setPrice(PRODUCT_PRICE);
        product.setImageUrl(PRODUCT_IMAGE_URL);
        products.add(product);

        given(productPort.findAll()).willReturn(products);
        given(productPort.findById(1L)).willReturn(product);
        given(productPort.findById(100L)).willThrow(ProductNotFoundException.class);
    }

    @Test
    void getProducts() {
        assertThat(productService.getProducts()).isNotEmpty();
        assertThat(productService.getProducts()).hasSize(1);
    }

    @Test
    void getProductWithExistingId() {
        Product product = productService.getProduct(1L);

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL);

        verify(productPort).findById(1L);
    }

    @Test
    void getProductWithNotExistingId() {
        assertThatThrownBy(() -> productService.getProduct(100L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void createProduct() {
        Product product = new Product();
        product.setName(PRODUCT_NAME);

        productService.createProduct(product);

        verify(productPort).save(product);
    }

    @Test
    void updateProductWithExistingId() {
        Product source = new Product();
        source.setName("updated name");

        given(productPort.save(any(Product.class))).willReturn(source);
        Product product = productService.updateProduct(1L, source);

        verify(productPort).findById(1L);
        verify(productPort).save(any(Product.class));
        assertThat(product.getName()).isEqualTo("updated name");
    }

    @Test
    void updateProductWithNotExistingId() {
        Product source = new Product();
        source.setName("updated name");

        assertThatThrownBy(() -> productService.updateProduct(100L, source))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productPort).findById(100L);
    }

    @Test
    void deleteProductWithExistingId() {
        productService.deleteProduct(1L);

        verify(productPort).findById(1L);
        verify(productPort).delete(any(Product.class));
    }

    @Test
    void deleteProductWithNotExistingId() {
        assertThatThrownBy(() -> productService.deleteProduct(100L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productPort).findById(100L);
    }

}
