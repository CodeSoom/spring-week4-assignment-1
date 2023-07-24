package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.dto.ProductRequest;
import com.codesoom.assignment.product.domain.dto.ProductResponse;
import com.codesoom.assignment.product.infra.persistence.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    private static final String PRODUCT_NAME = "product";
    private static final String PRODUCT_MAKER = "maker";
    private static final Integer PRODUCT_PRICE = 1000;
    private static final String PRODUCT_IMAGE_URL = "image";

    ProductService productService;
    ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    @DisplayName("상품을 생성하면, 생성된 상품이 반환된다.")
    public void testCreateProduct() {
        // given
        ProductRequest productRequest = new ProductRequest(PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_IMAGE_URL);

        Product savedProduct = Product.builder()
                .id(1L)
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .imageUrl(PRODUCT_IMAGE_URL)
                .price(PRODUCT_PRICE)
                .build();

        given(productRepository.save(any(Product.class))).willReturn(savedProduct);

        // when
        ProductResponse response = productService.createProduct(productRequest);

        // then
        assertThat(response.getId()).isEqualTo(savedProduct.getId());
        assertThat(response.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(response.getMaker()).isEqualTo(PRODUCT_MAKER);
        assertThat(response.getPrice()).isEqualTo(PRODUCT_PRICE);
        assertThat(response.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL);
    }

    @Test
    @DisplayName("상품 목록을 조회하면, 상품 목록이 반환된다.")
    public void testGetProductList() {
        // given
        Product product1 = Product.builder()
                .id(1L)
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .imageUrl(PRODUCT_IMAGE_URL)
                .price(PRODUCT_PRICE)
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .imageUrl(PRODUCT_IMAGE_URL)
                .price(PRODUCT_PRICE)
                .build();

        given(productRepository.findAll()).willReturn(Arrays.asList(product1, product2));

        // when
        List<ProductResponse> responseList = productService.getProductList();

        // then
        assertThat(responseList)
                .hasSize(2)
                .extracting("name", "maker", "price", "imageUrl")
                .containsExactly(
                        tuple(PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_IMAGE_URL),
                        tuple(PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_IMAGE_URL)
                );
    }

    @Test
    @DisplayName("상품을 아이디로 조회하면, 조회된 상품이 반환된다.")
    public void testGetProduct() {
        // given
        Long id = 1L;
        Product product = Product.builder()
                .id(id)
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .imageUrl(PRODUCT_IMAGE_URL)
                .price(PRODUCT_PRICE)
                .build();

        given(productRepository.findById(id)).willReturn(Optional.of(product));

        // when
        ProductResponse response = productService.getProduct(id);

        // then
        assertThat(response)
                .extracting("id", "name", "maker", "price", "imageUrl")
                .containsExactly(id, PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_IMAGE_URL);
    }

    @Test
    @DisplayName("아이디와 수정할 상품 정보를 받으면, 해당 상품이 수정된다.")
    public void testUpdateProduct() {
        // given
        Long id = 1L;
        ProductRequest productRequest = new ProductRequest(PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_IMAGE_URL);

        Product existingProduct = Product.builder()
                .id(id)
                .name("Existing name")
                .maker("Existing maker")
                .imageUrl("Existing image url")
                .price(2000)
                .build();

        Product updatedProduct = Product.builder()
                .id(id)
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .imageUrl(PRODUCT_IMAGE_URL)
                .price(PRODUCT_PRICE)
                .build();

        given(productRepository.findById(id)).willReturn(Optional.of(existingProduct));
        given(productRepository.save(any(Product.class))).willReturn(updatedProduct);

        // when
        ProductResponse response = productService.updateProduct(id, productRequest);

        // then
        assertThat(response)
                .extracting("id", "name", "maker", "price", "imageUrl")
                .containsExactly(id, PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_IMAGE_URL);
    }

    @Test
    @DisplayName("아이디로 상품을 삭제하면, 해당 상품이 삭제된다.")
    public void testDeleteProduct() {
        // given
        Long id = 1L;
        Product product = Product.builder()
                .id(id)
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .imageUrl(PRODUCT_IMAGE_URL)
                .price(PRODUCT_PRICE)
                .build();

        given(productRepository.findById(id)).willReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        // when
        productService.deleteProduct(id);

        // then
        verify(productRepository, times(1)).delete(product);
    }
}
