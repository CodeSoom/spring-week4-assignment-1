package com.codesoom.assignment.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.codesoom.assignment.domain.Product;

// TODO 테스트 코드
// 1. CREATE 테스트 코드 작성
// 2. READ 테스트 코드 작성
// 3. UPDATE 테스트 코드 작성
// 4. DELETE 테스트 코드 작성

@DataJpaTest
class ProductRepositoryTest {

    private Product product;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        product = new Product();

        product.setName("야광볼");
        product.setPrice(10000L);
        product.setImage("/Users/admin/static/image.jpg");
    }

    @Test
    void saveTest() {
        productRepository.save(product);
        assertThat(product.getId()).isEqualTo(1L);
    }

    @Test
    void findAllTest() {
        productRepository.findAll();
    }

    @Test
    void findByIdTest() {
        productRepository.findById(1L);
    }

    @Test
    void deleteTest() {
        productRepository.delete(product);
    }
}
