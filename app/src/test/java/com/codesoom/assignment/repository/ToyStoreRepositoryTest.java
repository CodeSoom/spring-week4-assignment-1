package com.codesoom.assignment.repository;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.codesoom.assignment.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ToyStoreRepositoryTest {

    @Autowired
    ToyStoreRepository toyStoreRepository;

    @Test
    void saveTest() {
        Product product = new Product("name","maker",5000,"abc.jpg");

        Product savedProduct = toyStoreRepository.save(product);

        assertThat(savedProduct).isEqualTo(1L);
    }
}
