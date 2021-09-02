package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import com.codesoom.assignment.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.codesoom.assignment.Constant.IMAGE_URL;
import static com.codesoom.assignment.Constant.MAKER;
import static com.codesoom.assignment.Constant.NAME;
import static com.codesoom.assignment.Constant.PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductService 서비스 테스트")
class ProductServiceTest {

    private ProductService service;
    private ProductRepository repository;

    private Product product;

    @BeforeEach
    void setUp() {

        repository = mock(ProductRepository.class);

        service = new ProductService(repository);

        setUpFixtures();
    }

    private void setUpFixtures() {
        product = new Product(1L, NAME, MAKER, PRICE, IMAGE_URL);

        given(repository.findById(1L)).willReturn(Optional.of(product));
        given(repository.findById(100L)).willReturn(Optional.empty());
        given(repository.save(any(Product.class))).willReturn(product);

    }

    @DisplayName("제품들이 비어있는 목록을 조회할 수 있습니다.")
    @Test
    void findAllNotExistsProduct() {
        given(service.findAll()).willReturn(new ArrayList<>());

        List<Product> products = service.findAll();

        assertThat(products.isEmpty()).isTrue();

        verify(repository).findAll();
    }

    @DisplayName("제품들이 존재하는 목록을 조회할 수 있습니다.")
    @Test
    void findAllExistsProduct() {
        given(service.findAll())
                .willReturn(Arrays.asList(product));
        List<Product> products = service.findAll();

        assertThat(products).hasSize(1);

        verify(repository).findAll();
    }


    @DisplayName("식별자를 통해 제품을 찾을 수 있습니다.")
    @Test
    void findProductById() {
        assertThat(service.findById(1L)).isEqualTo(product);
        verify(repository).findById(1L);
    }

    @DisplayName("존재하지 않는 식별자를 통해 상품을 찾을 경우 예외가 발생합니다.")
    @Test
    void findProductByNotExistsId() {
        assertThatThrownBy(() -> service.findById(100L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("새로운 상품을 등록할 수 있습니다.")
    @Test
    void saveProduct() {
        final Product product = Product.of("Other", MAKER, PRICE, IMAGE_URL);

        assertThat(product.getId()).isNull();

        Product createdProduct = service.save(product);

        assertThat(createdProduct.getId()).isNotNull();
        assertThat(createdProduct.getMaker()).isEqualTo(MAKER);
        assertThat(createdProduct.getPrice()).isEqualTo(PRICE);
        assertThat(createdProduct.getImageUrl()).isEqualTo(IMAGE_URL);

        verify(repository).save(product);
    }

    @DisplayName("상품의 정보를 수정할 수 있습니다. ")
    @Test
    void updateProduct() {
        final Product newProduct = Product.of("Other", "OtherMaker", 3000L, "OtherUrl");
        Product updatedProduct = service.updateProduct(1L, newProduct);

        assertThat(updatedProduct.getName()).isEqualTo(newProduct.getName());
        assertThat(updatedProduct.getMaker()).isEqualTo(newProduct.getMaker());
        assertThat(updatedProduct.getPrice()).isEqualTo(newProduct.getPrice());
        assertThat(updatedProduct.getImageUrl()).isEqualTo(newProduct.getImageUrl());

    }

    @DisplayName("존재하지 않는 식별자의 상품을 수정하려하면 예외가 발생합니다.")
    @Test
    void updateProductNotExistsId() {
        final Product newProduct = Product.of("Other", "OtherMaker", 3000L, "OtherUrl");

        assertThatThrownBy(() -> service.updateProduct(100L, newProduct))
                .isInstanceOf(ProductNotFoundException.class);

        verify(repository).findById(100L);
    }

    @DisplayName("상품을 삭제할 수 있습니다.")
    @Test
    void deleteProduct() {

        final Product foundProduct = service.findById(1L);
        service.deleteProduct(foundProduct);

        verify(repository).findById(1L);
        verify(repository).delete(any(Product.class));
    }

    @DisplayName("존재하지 않는 식별자의 상품을 삭제하려 하면 예외가 발생합니다.")
    @Test
    void deleteProductNotExistsId() {
        assertThatThrownBy(() -> {
            final Product foundProduct = service.findById(100L);
            service.deleteProduct(foundProduct);
        })
                .isInstanceOf(ProductNotFoundException.class);

        verify(repository).findById(100L);
    }


}
