package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        product = Product.builder()
                .name("장난감")
                .maker("장난감 메이커")
                .price(10000)
                .imageUrl("url")
                .build();

        List<Product> products = new ArrayList<>();
        products.add(product);

        given(productRepository.save(any(Product.class)))
                .will(invocation -> invocation.<Product>getArgument(0));

        given(productRepository.findAll()).willReturn(products);
        given(productRepository.findById(existingId)).willReturn(Optional.of(product));
        given(productRepository.findById(notExistingId)).willReturn(Optional.empty());
    }

    @Test
    @DisplayName("상품을 생성한다.")
    void createProduct() {
        Product addedProduct = productService.createProduct(product);

        verify(productRepository).save(product);

        assertThat(addedProduct).isEqualTo(product);
    }

    @Test
    @DisplayName("모든 상품을 조회한다.")
    void getProducts() {
        List<Product> products = productService.getProducts();

        verify(productRepository).findAll();

        assertThat(products).hasSize(1);
    }

    @Test
    @DisplayName("존재하는 id로 상품을 조회하면 id에 해당하는 상품을 리턴한다.")
    void getProductWithExistingId() {
        Product foundProduct = productService.getProduct(existingId);

        verify(productRepository).findById(existingId);

        assertThat(foundProduct).isEqualTo(product);
    }

    @Test
    @DisplayName("존재하지 않는 id로 상품을 조회하면 '상품을 찾을 수 없다' 는 예외가 발생한다.")
    void getProductWithNotExistingId() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(notExistingId));

        verify(productRepository).findById(notExistingId);
    }

    @Test
    @DisplayName("존재하는 id로 상품을 수정하면 수정된 상품을 리턴한다.")
    void updateProductWithExistingId() {
        Product newProduct = Product.builder()
                .name("new 장난감")
                .maker("new 장난감 메이커")
                .price(20000)
                .imageUrl("new url")
                .build();

        Product updatedProduct = productService.updateProduct(existingId, newProduct);

        verify(productRepository).findById(existingId);

        assertThat(updatedProduct.getName()).isEqualTo(newProduct.getName());
        assertThat(updatedProduct.getMaker()).isEqualTo(newProduct.getMaker());
        assertThat(updatedProduct.getPrice()).isEqualTo(newProduct.getPrice());
        assertThat(updatedProduct.getImageUrl()).isEqualTo(newProduct.getImageUrl());
    }

    @Test
    @DisplayName("존재하지 않는 id로 상품을 수정하면 '상품을 찾을 수 없다' 는 예외가 발생한다.")
    void updateProductWithNotExistingId() {
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(notExistingId, product));

        verify(productRepository).findById(notExistingId);
    }

    @Test
    @DisplayName("존재하는 id로 상품을 삭제하면 상품을 삭제한다.")
    void deleteProductWithExistingId() {
        productService.deleteProduct(existingId);

        verify(productRepository).findById(existingId);
        verify(productRepository).delete(any(Product.class));
    }

    @Test
    @DisplayName("존재하지 않는 id로 상품을 삭제하면 '상품을 찾을 수 없다' 는 예외가 발생한다.")
    void deleteProductWithNotExistingId() {
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(notExistingId));

        verify(productRepository).findById(notExistingId);
    }

}
