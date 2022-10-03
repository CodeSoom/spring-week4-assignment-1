package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("findAll method")
    @Nested
    class Describe_findAll {

        @DisplayName("if nothing is added")
        @Nested
        class Context {
            @DisplayName("returns an empty list ")
            @Test
            void it_returns_empty() {
                List<Product> all = productService.findAll();
                assertThat(all.size()).isEqualTo(0);
            }
        }

    }

}
