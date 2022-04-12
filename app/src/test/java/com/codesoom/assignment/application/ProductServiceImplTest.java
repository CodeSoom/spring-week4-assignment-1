package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.entity.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("ProductServiceImpl 에서")
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("모든 Product 객체를 불러올 때")
    class Describe_of_readAll_products {

    }
}