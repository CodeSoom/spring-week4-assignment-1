package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    private Product product1;
    private Product product2;

    private List<Product> products;
    private final Long NOT_STORED_ID = 100L;
    private final String PRODUCT_NAME = "name1";

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        product1 = Product.builder()
                .id(1L)
                .name(PRODUCT_NAME)
                .price(1)
                .imageUrl("url1")
                .maker("maker1")
                .build();

        product2 = Product.builder()
                .id(2L)
                .name("name2")
                .price(2)
                .imageUrl("url2")
                .maker("maker2")
                .build();

        products = new ArrayList<>();

        given(productRepository.save(any(Product.class)))
                .will(invocation -> invocation.<Product>getArgument(0));

        given(productRepository.findAll()).willReturn(products);

        given(productRepository.findById(product1.getId())).willReturn(Optional.of(product1));

        given(productRepository.findById(NOT_STORED_ID)).willReturn(Optional.empty());
    }

    @Test
    @DisplayName("createProduct")
    void createProduct() {
        productService.createProduct(product1);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("getProducts")
    void getProducts() {
        products.add(product1);
        products.add(product2);

        List<ProductResponse> products = productService.getProducts();

        assertThat(products).hasSize(2);

        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("저장된 id로 상품을 조회하면 해당하는 상품을 리턴한다.")
    void getProductWithExistingId() {
        Long id = product1.getId();

        ProductResponse productResponse = productService.getProduct(id);

        assertThat(productResponse.getProduct().getName()).isEqualTo(PRODUCT_NAME);

        verify(productRepository).findById(id);
    }

    @Test
    @DisplayName("저장되지 않은 id로 상품을 조회하면 ProductNotFoundException 예외를 던진다.")
    void getProductWithNotStoredId() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(NOT_STORED_ID));

        verify(productRepository).findById(NOT_STORED_ID);
    }

    @Test
    @DisplayName("저장된 id로 상품을 업데이트하면 업데이트한 상품을 리턴한다.")
    void updateProductWithExistingId() {
        Long id = product1.getId();

        ProductResponse updatedProduct = productService.updateProduct(id, product2);

        verify(productRepository).findById(id);

        assertAll(
                () -> assertThat(updatedProduct.getProduct().getName()).isEqualTo(product2.getName()),
                () -> assertThat(updatedProduct.getProduct().getMaker()).isEqualTo(product2.getMaker()),
                () -> assertThat(updatedProduct.getProduct().getPrice()).isEqualTo(product2.getPrice()),
                () -> assertThat(updatedProduct.getProduct().getImageUrl()).isEqualTo(product2.getImageUrl())
        );
    }

    @Test
    @DisplayName("저장되지 않은 id로 상품을 업데이트하면 ProductNotFoundException 예외를 던진다.")
    void updateProductWithNotStoredId() {
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(NOT_STORED_ID, product1));

        verify(productRepository).findById(NOT_STORED_ID);
    }

    @Test
    @DisplayName("저장된 id로 상품을 삭제하면 상품을 삭제한다.")
    void deleteProductWithStoredId() {
        Long id = product1.getId();

        productService.deleteProduct(id);

        verify(productRepository).findById(id);
        verify(productRepository).delete(any(Product.class));
    }

    @Test
    @DisplayName("저장되지 않은 id로 상품을 삭제하면 ProductNotFoundException 예외를 던진다.")
    void deleteProductWithNotStoredId() {
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(NOT_STORED_ID));

        verify(productRepository).findById(NOT_STORED_ID);
    }
}
