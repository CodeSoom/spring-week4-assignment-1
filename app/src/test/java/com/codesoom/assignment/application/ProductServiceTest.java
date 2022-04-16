package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {
    private ProductService service;
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(ProductRepository.class);
        service = new ProductService(repository);

        List<Product> products = new ArrayList<>();
        given(repository.findAll()).willReturn(products);
    }

    @Test
    void getProducts() {
        List<Product> products = service.getProducts();
        verify(repository).findAll();
        assertThat(products).hasSize(0);
    }
}