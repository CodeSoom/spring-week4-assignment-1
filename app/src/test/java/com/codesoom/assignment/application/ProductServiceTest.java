package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import java.util.Collections;
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
    }

    @Test
    @DisplayName("상품 하나를 생성한다")
    void create() {
        Product createdProduct = productService.create(product);

        verify(productRepository).save(createdProduct);

        assertThat(createdProduct).isEqualTo(product);
    }

    @Test
    @DisplayName("모든 상품을 조회한다")
    void findAll() {
        Iterable<Product> products = productService.findAll();

        verify(productRepository).findAll();

        assertThat(products).isEqualTo(Collections.singletonList(product));
    }
}
