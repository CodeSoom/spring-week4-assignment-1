package com.codesoom.assignment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ToyStoreRepository;
import java.util.ArrayList;
import java.util.List;
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

        given(toyStoreService.getProducts()).willReturn(productsSource);

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

        given(toyStoreService.getProducts()).willReturn(productsSource);

        List<Product> products = toyStoreService.getProducts();

        verify(toyStoreRepository).findAll();

        assertThat(products).hasSize(3);
    }
}
