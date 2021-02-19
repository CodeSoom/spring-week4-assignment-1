package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductRequest;
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
    private ProductRequest productRequest;
    private List<Product> products;

    private final Long existingId = 1L;
    private final Long notExistingId = 100L;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        product1 = Product.builder()
                .id(1L)
                .name("장난감1")
                .maker("장난감 메이커1")
                .price(10000)
                .imageUrl("url1")
                .build();

        product2 = Product.builder()
                .id(2L)
                .name("장난감2")
                .maker("장난감 메이커2")
                .price(20000)
                .imageUrl("url2")
                .build();

        productRequest = new ProductRequest();
        productRequest.setName("장난감");
        productRequest.setMaker("장난감 메이커");
        productRequest.setPrice(10000);
        productRequest.setImageUrl("url");

        products = new ArrayList<>();
    }

    @Test
    @DisplayName("상품을 생성한다.")
    void createProduct() {
        given(productRepository.save(any(Product.class)))
                .will(invocation -> invocation.<Product>getArgument(0));

        productService.createProduct(productRequest);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("모든 상품을 조회한다.")
    void getProducts() {
        products.add(product1);
        products.add(product2);

        given(productRepository.findAll()).willReturn(products);

        List<ProductResponse> products = productService.getProducts();

        verify(productRepository).findAll();

        assertThat(products).hasSize(2);
    }

    @Test
    @DisplayName("존재하는 id로 상품을 조회하면 id에 해당하는 상품을 리턴한다.")
    void getProductWithExistingId() {
        given(productRepository.findById(existingId)).willReturn(Optional.of(product1));

        productService.getProduct(existingId);

        verify(productRepository).findById(existingId);
    }

    @Test
    @DisplayName("존재하지 않는 id로 상품을 조회하면 '상품을 찾을 수 없다' 는 예외가 발생한다.")
    void getProductWithNotExistingId() {
        given(productRepository.findById(notExistingId)).willReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(notExistingId));

        verify(productRepository).findById(notExistingId);
    }

    @Test
    @DisplayName("존재하는 id로 상품을 수정하면 수정된 상품을 리턴한다.")
    void updateProductWithExistingId() {
        given(productRepository.findById(existingId)).willReturn(Optional.of(product1));

        ProductRequest updateRequest = new ProductRequest();
        updateRequest.setName("new 장난감");
        updateRequest.setMaker("new 장난감 메이커");
        updateRequest.setPrice(20000);
        updateRequest.setImageUrl("new url");

        ProductResponse updatedProduct = productService.updateProduct(existingId, updateRequest);


        verify(productRepository).findById(existingId);

        assertAll(
                () -> assertThat(updatedProduct.getName()).isEqualTo(updateRequest.getName()),
                () -> assertThat(updatedProduct.getMaker()).isEqualTo(updateRequest.getMaker()),
                () -> assertThat(updatedProduct.getPrice()).isEqualTo(updateRequest.getPrice()),
                () -> assertThat(updatedProduct.getImageUrl()).isEqualTo(updateRequest.getImageUrl())
        );
    }

    @Test
    @DisplayName("존재하지 않는 id로 상품을 수정하면 '상품을 찾을 수 없다' 는 예외가 발생한다.")
    void updateProductWithNotExistingId() {
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(notExistingId, productRequest));

        verify(productRepository).findById(notExistingId);
    }

    @Test
    @DisplayName("존재하는 id로 상품을 삭제하면 상품을 삭제한다.")
    void deleteProductWithExistingId() {
        given(productRepository.findById(existingId)).willReturn(Optional.of(product1));

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
