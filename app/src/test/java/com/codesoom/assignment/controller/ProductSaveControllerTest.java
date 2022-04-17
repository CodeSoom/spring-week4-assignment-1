package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductSaveServiceImpl;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("ProductDeleteController 클래스")
@ActiveProfiles("test")
@SpringBootTest
public class ProductSaveControllerTest {

    private ProductSaveController controller;

    @Autowired
    private ProductSaveServiceImpl service;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setup() {
        this.controller = new ProductSaveController(service);
        repository.deleteAll();
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    @DisplayName("상품을 성공적으로 등록한다.")
    @Test
    void createTest() {
        final ProductDto productDto
                = new ProductDto("어쩌구", "어쩌구컴퍼니", BigDecimal.valueOf(2000), "url");

        Product product = controller.saveProduct(productDto);

        assertThat(product.getName()).isEqualTo(productDto.getName());
        assertThat(repository.findById(product.getId())).isNotEmpty();
    }

}
