package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;
    private Product product;

    private final Long existingId = 1L;
    private final Long notExistingId = 100L;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
        product = new Product("장난감", "장난감 메이커", 10000, "url");

        List<Product> products = new ArrayList<>();
        products.add(product);

        given(productRepository.save(any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(0);
            return product;
        });

        given(productRepository.findAll()).willReturn(products);
        given(productRepository.findById(existingId)).willReturn(Optional.of(product));
        given(productRepository.findById(notExistingId)).willReturn(Optional.empty());
    }

    @Test
    void createProduct() {
        Product addedProduct = productService.createProduct(product);

        verify(productRepository).save(product);

        assertThat(addedProduct).isEqualTo(product);
    }

    @Test
    void getProducts() {
        List<Product> products = productService.getProducts();

        verify(productRepository).findAll();

        assertThat(products).hasSize(1);
    }

    @Test
    void getProductWithExistingId() {
        Product foundProduct = productService.getProduct(existingId);

        verify(productRepository).findById(existingId);

        assertThat(foundProduct).isEqualTo(product);
    }

    @Test
    void getProductWithNotExistingId() {
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProduct(notExistingId);
        });

        verify(productRepository).findById(notExistingId);
    }

    @Test
    void updateProductWithExistingId() {
        Product newProduct = new Product("새로운 장난감", "새로운 장난감 메이커", 20000, "new url");
        Product updatedProduct = productService.updateProduct(existingId, newProduct);

        verify(productRepository).findById(existingId);

        assertThat(updatedProduct.getName()).isEqualTo(newProduct.getName());
        assertThat(updatedProduct.getMaker()).isEqualTo(newProduct.getMaker());
        assertThat(updatedProduct.getPrice()).isEqualTo(newProduct.getPrice());
        assertThat(updatedProduct.getImageUrl()).isEqualTo(newProduct.getImageUrl());
    }

    @Test
    void updateProductWithNotExistingId() {
        assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(notExistingId, product);
        });

        verify(productRepository).findById(notExistingId);
    }

    @Test
    void deleteProductWithExistingId() {
        productService.deleteProduct(existingId);

        verify(productRepository).findById(existingId);
        verify(productRepository).delete(any(Product.class));
    }

    @Test
    void deleteProductWithNotExistingId() {
        assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(notExistingId);
        });

        verify(productRepository).findById(notExistingId);
    }

}
