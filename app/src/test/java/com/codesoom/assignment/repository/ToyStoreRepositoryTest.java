package com.codesoom.assignment.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.codesoom.assignment.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToyStoreRepositoryTest {

    @Autowired
    ToyStoreRepository toyStoreRepository;

    @Test
    void saveTest() {
        Product product = new Product("name", "maker", 5000, "abc.jpg");

        Product savedProduct = toyStoreRepository.save(product);

        assertThat(savedProduct.getName()).isEqualTo("name");
        assertThat(savedProduct.getMaker()).isEqualTo("maker");
        assertThat(savedProduct.getPrice()).isEqualTo(5000);
        assertThat(savedProduct.getImageUrl()).isEqualTo("abc.jpg");
    }
}
