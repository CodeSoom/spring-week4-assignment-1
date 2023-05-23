package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository = mock(ProductRepository.class);

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
        Product product = new Product("쥐돌이", "냥이월드", 5000);
        given(productRepository.findAll()).willReturn(List.of(product));
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(productRepository.findById(1000L)).willReturn(null);
        given(productRepository.save(any())).will(invocation -> {
            Product source = invocation.getArgument(0);
            return new Product(2L,
                    source.getName(),
                    source.getMaker(),
                    source.getPrice());
        });
    }

    @Test
    public void getProductsWithNoProduct() {
        given(productRepository.findAll()).willReturn(List.of());
        assertThat(productService.getProducts()).isEmpty();
    }

    @Test
    public void getProductsWithProduct() {
        List<Product> products = productService.getProducts();
        assertThat(products).isNotEmpty();
        var product = products.get(0);
        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

    @Test
    public void getProductWithExistId() {
        Product product = productService.getProduct(1L);
        assertThat(product).isNotNull();
    }

    @Test
    public void getProductWithNotExistId() {
        assertThatThrownBy(() -> productService.getProduct(1000L));
    }

    @Test
    public void createProduct() {
        Product source = new Product("쥐돌이", "냥이월드", 5000);
        Product product = productService.createProduct(source);
        verify(productRepository).save(any(Product.class));

        assertThat(product.getId()).isEqualTo(2L);
        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

    @Test
    public void updateProduct() {
        Product source = new Product("쥐돌이", "냥이월드", 5000);
        Product product = productService.updateProduct(1L, new Product("쥐돌이", "냥이월드", 5000));
        verify(productRepository).save(any(Product.class));

        assertThat(product.getId()).isEqualTo(2L);
        assertThat(product.getName()).isEqualTo("쥐돌이");
    }
}
