package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductRequest;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    private ProductRequest productRequest;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        product1 = Product.builder()
                .name("name1")
                .maker("maker1")
                .price(1)
                .imageUrl("url1")
                .build();

        product2 = Product.builder()
                .name("name2")
                .maker("maker2")
                .price(2)
                .imageUrl("url2")
                .build();

        productRequest = new ProductRequest();
        productRequest.setName("name3");
        productRequest.setMaker("maker3");
        productRequest.setPrice(3);
        productRequest.setImageUrl("url3");
    }

    @Test
    @DisplayName("상품을 생성한다.")
    void createProduct() {
        given(productRepository.save(any(Product.class)))
                .will(invocation -> invocation.getArgument(0));

        productService.createProduct(productRequest);
    }

}
