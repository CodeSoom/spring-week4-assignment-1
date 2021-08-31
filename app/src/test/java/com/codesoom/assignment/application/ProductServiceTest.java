package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductServiceTest {

    private Product product;
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        product = new Product("name", "maker", 10000L, "imageUrl");
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        given(productRepository.save(product)).willReturn(product);

        given(productRepository.findAll()).willReturn(Collections.singletonList(product));

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
    }

    @Test
    @DisplayName("상품 하나를 생성한다")
    void create() {
        Product createdProduct = productService.create(product);

        assertThat(createdProduct).isEqualTo(product);

        verify(productRepository).save(createdProduct);
    }

    @Test
    @DisplayName("모든 상품을 조회한다")
    void findAll() {
        Iterable<Product> products = productService.findAll();

        assertThat(products).isEqualTo(Collections.singletonList(product));

        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("존재하는 상품일 경우 상품 하나를 찾아 조회한다")
    void findById() {
        Product foundProduct = productService.findById(1L);

        assertThat(foundProduct).isEqualTo(product);

        verify(productRepository).findById(1L);
    }

    @Test
    @DisplayName("존재하는 상품일 경우 상품 하나를 찾아 수정한다")
    void update() {
        long id = 1L;
        Product source = new Product("a", "b", 100L, "c");

        Product updatedProduct = productService.update(id, source);

        assertThat(source).isEqualTo(updatedProduct);

        verify(productRepository).findById(id);
        verify(productRepository).save(source);
    }
}
