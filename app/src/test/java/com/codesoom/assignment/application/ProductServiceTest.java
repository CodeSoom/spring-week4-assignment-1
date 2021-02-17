package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductServiceTest {

    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {

        List<Product> products = new ArrayList<>();

        given(productRepository.findAll()).willReturn(products);
    }

    @Test
    void getProducts() {

        productService.getProducts();
        verify(productRepository).findAll();
    }
}
