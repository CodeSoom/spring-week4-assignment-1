package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductService 클래스")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;


    @BeforeEach
    void setUp() {
        this.productService = new ProductService(productRepository);
    }

    @Test
    @DisplayName("getList는 모든 상품의 목록을 반환한다.")
    void getList() {
        assertThat(productService.getList()).hasSize(0);
    }

}