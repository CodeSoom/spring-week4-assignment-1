package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.infra.ProductRepository;
import com.codesoom.assignment.product.ui.dto.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("ProductService 클래스")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    private static final Long PRODUCT1_ID = 1L;
    private static final Long PRODUCT2_ID = 2L;
    private static final Long NOT_EXIST_ID = -1L;
    private static final String PRODUCT1_NAME = "product1";
    private static final String PRODUCT2_NAME = "product2";
    private static final String PRODUCT1_MAKER = "maker1";
    private static final String PRODUCT2_MAKER = "maker2";
    private static final String PRODUCT1_IMAGE = "https://http.cat/599";
    private static final String PRODUCT2_IMAGE = "https://http.cat/200";
    private static final int PRODUCT1_PRICE = 10_000;
    private static final int PRODUCT2_PRICE = 20_000;

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private List<Product> products;

    private Product product1;
    private Product product2;

    private ProductResponseDto responseDto1;
    private ProductResponseDto responseDto2;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);

        product1 = Product.builder()
                .id(PRODUCT1_ID)
                .name(PRODUCT1_NAME)
                .maker(PRODUCT1_MAKER)
                .price(PRODUCT1_PRICE)
                .imageUrl(PRODUCT1_IMAGE)
                .build();

        product2 = Product.builder()
                .id(PRODUCT2_ID)
                .name(PRODUCT2_NAME)
                .maker(PRODUCT2_MAKER)
                .price(PRODUCT2_PRICE)
                .imageUrl(PRODUCT2_IMAGE)
                .build();

        products = Arrays.asList(product1, product2);
        responseDto1 = new ProductResponseDto(product1);
        responseDto2 = new ProductResponseDto(product2);
    }

    @Test
    @DisplayName("등록된 상품 목록을 리턴한다")
    void getProducts() {
        given(productRepository.findAll()).willReturn(products);

        List<ProductResponseDto> products = productService.getProducts();

        assertThat(products).containsExactly(responseDto1, responseDto2);
    }

    @Test
    @DisplayName("등록된 상품 id에 해당하는 상품을 리턴한다")
    void getProductWithValidId() {
        given(productRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(product1));

        ProductResponseDto actual = productService.getProduct(anyLong());

        assertAll(
                () -> assertThat(actual).isEqualTo(responseDto1),
                () -> assertThat(actual.getId()).isEqualTo(PRODUCT1_ID)
        );
    }

    @Test
    @DisplayName("등록되지 않은 상품 id로 상품 조회시 예외발생")
    void getProductWithInValidId() {
        given(productRepository.findById(anyLong()))
                .willThrow(new ProductNotFoundException(NOT_EXIST_ID));

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() ->productService.getProduct(anyLong()));
    }
}
