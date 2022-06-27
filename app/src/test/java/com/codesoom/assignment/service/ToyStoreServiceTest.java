package com.codesoom.assignment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ToyStoreRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ToyStoreServiceTest {

    ToyStoreService toyStoreService;

    ToyStoreRepository toyStoreRepository;

    @BeforeEach
    void setUp() {
        toyStoreRepository = mock(ToyStoreRepository.class);

        toyStoreService = new ToyStoreService(toyStoreRepository);
    }

    @DisplayName("`이름, 메이커, 가격, 이미지 URL`을 입력 받아 장난감 등록")
    @Test
    void addToyTest() {
        Product sourceProduct = new Product("name", "maker", 5000, "abc.jpg");

        given(toyStoreService.save(any(Product.class))).will(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setId(1L);
            return savedProduct;
        });

        Product product = toyStoreService.save(sourceProduct);

        verify(toyStoreRepository).save(any(Product.class));

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("name");
        assertThat(product.getMaker()).isEqualTo("maker");
        assertThat(product.getPrice()).isEqualTo(5000);
        assertThat(product.getImageUrl()).isEqualTo("abc.jpg");

    }

    @DisplayName("등록된 장난감 비어있는 목록 조회")
    @Test
    void getEmptyProductsTest() {
        List<Product> productsSource = new ArrayList<>();

        given(toyStoreRepository.findAll()).willReturn(productsSource);

        List<Product> products = toyStoreService.getProducts();

        verify(toyStoreRepository).findAll();

        assertThat(products).hasSize(0);
    }

    @DisplayName("등록된 장난감 비어있지 않은 목록 조회")
    @Test
    void getNotEmptyProductsTest() {
        List<Product> productsSource = new ArrayList<>();
        productsSource.add(new Product("name", "maker", 10000, "abcdefg.jpg"));
        productsSource.add(new Product("name", "maker", 10000, "abcdefg.jpg"));
        productsSource.add(new Product("name", "maker", 10000, "abcdefg.jpg"));

        given(toyStoreRepository.findAll()).willReturn(productsSource);

        List<Product> products = toyStoreService.getProducts();

        verify(toyStoreRepository).findAll();

        assertThat(products).hasSize(3);
    }

    @DisplayName("유효한 id로 장난감 목록 조회")
    @Test
    void getProductWithValidId() {
        long productId = 1L;

        Product sourceProduct = new Product(productId, "name", "maker", 10000, "abcdefg.jpg");

        given(toyStoreRepository.findById(productId)).willReturn(Optional.of(sourceProduct));

        Product product = toyStoreService.getProduct(productId);

        verify(toyStoreRepository).findById(productId);

        assertThat(product.getId()).isEqualTo(productId);
    }

    @DisplayName("유효하지 않은 id로 장난감 목록 조회")
    @Test
    void getProductWithInValidId() {
        long productId = 1000L;

        given(toyStoreRepository.findById(productId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            toyStoreService.getProduct(productId);
        }).isInstanceOf(ProductNotFoundException.class);

        verify(toyStoreRepository).findById(productId);
    }

    @DisplayName("유효한 id로 장난감 삭제")
    @Test
    void deleteProductWithValidId() {
        long productId = 1L;

        Product sourceProduct = new Product(productId, "name", "maker", 10000, "abcdefg.jpg");

        given(toyStoreRepository.findById(productId)).willReturn(Optional.of(sourceProduct));

        Product product = toyStoreService.deleteProduct(productId);

        verify(toyStoreRepository).findById(productId);
        verify(toyStoreRepository).delete(product);
    }

    @DisplayName("유효하지 않은 id로 장난감 삭제")
    @Test
    void deleteProductWithInValidId() {
        long productId = 1000L;

        given(toyStoreRepository.findById(productId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            toyStoreService.deleteProduct(productId);
        }).isInstanceOf(ProductNotFoundException.class);

        verify(toyStoreRepository).findById(productId);
    }

    @DisplayName("유효한 id로 장난감 정보 수정")
    @Test
    void updateProductWithValidId() {
        long productId = 1L;

        Product sourceProduct = new Product(productId, "name", "maker", 10000, "abcdefg.jpg");
        Product updateProduct = new Product(productId, "nameChange", "maker", 10000, "abcdefg.jpg");

        given(toyStoreRepository.findById(productId)).willReturn(Optional.of(sourceProduct));
        given(toyStoreRepository.save(sourceProduct)).willReturn(updateProduct);

        Product product = toyStoreService.updateProduct(productId, updateProduct);

        assertThat(product.getId()).isEqualTo(updateProduct.getId());
        assertThat(product.getName()).isEqualTo(updateProduct.getName());

        verify(toyStoreRepository).findById(productId);
        verify(toyStoreRepository).save(any(Product.class));
    }

    @DisplayName("유효하지 않은 id로 장난감 정보 수정")
    @Test
    void updateProductWithInvalidId() {
        long productId = 1000L;

        Product sourceProduct = new Product(productId, "name", "maker", 10000, "abcdefg.jpg");

        given(toyStoreRepository.findById(productId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            toyStoreService.updateProduct(productId, sourceProduct);
        }).isInstanceOf(ProductNotFoundException.class);

        verify(toyStoreRepository).findById(productId);
    }
}
