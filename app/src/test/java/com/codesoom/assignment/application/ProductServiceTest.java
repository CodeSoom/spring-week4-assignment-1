package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.models.Product;
import com.codesoom.assignment.models.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
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

        Product product = new Product(1L, "티셔츠", "나이키", 40000);

        given(productRepository.findAll()).willReturn(List.of(product));

        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        given(productRepository.save(any(Product.class))).will(invocation -> {
            Product source = invocation.getArgument(0);
            return new Product(
                    2L,
                    source.getName(),
                    source.getMaker(),
                    source.getPrice()
            );
        });

        given(productRepository.findById(1000L)).willReturn(Optional.empty());
    }

    @Test
    void getProductsWithValid() {
        List<Product> products = productService.getProducts();

        assertThat(productService.getProducts()).isNotEmpty();

        Product product = products.get(0);
        assertThat(product.getName()).isEqualTo("티셔츠");
    }

    @Test
    void getProductsWithInValid() {
        given(productRepository.findAll()).willReturn(List.of());

        assertThat(productService.getProducts()).isEmpty();
    }

    @Test
    void getProductWithInvalid() {
        Product product = productService.getProduct(1L);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("티셔츠");
    }

    @Test
    void getProductWithValid() {
        assertThatThrownBy(() -> productService.getProduct(1000L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(1000L);
    }

    @Test
    void createProduct() {
        Product product = productService
                .createProduct(new Product("티셔츠", "나이키", 40000));

        verify(productRepository).save(any(Product.class));

        assertThat(product.getId()).isEqualTo(2L);
        assertThat(product.getName()).isEqualTo("티셔츠");
    }

    @Test
    void updateProductWithValidId() {
        Product source = new Product("바지", "나이", 40000);
        Product product = productService.updateProduct(1L, source);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("바지");
    }

    @Test
    void updateProductWithInvalidId() {
        Product source = new Product("바지", "나이", 40000);

        assertThatThrownBy(() -> productService.updateProduct(1000L, source))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void deleteProductWithValidId() {
        Product product = productService.deleteProduct(1L);

        verify(productRepository).delete(any(Product.class));
    }

    @Test
    void deleteProductWithInvalidId() {
        assertThatThrownBy(() -> productService.deleteProduct(1000L))
                .isInstanceOf(ProductNotFoundException.class);
    }
}