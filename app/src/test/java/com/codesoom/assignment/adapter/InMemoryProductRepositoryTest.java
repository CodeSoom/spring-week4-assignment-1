package com.codesoom.assignment.adapter;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryProductRepositoryTest {
    ProductRepository inMemoryProductRepository;
    Product savedProduct;

    @BeforeEach
    void saveProduct() {
        String name = "고양이 인형";
        String maker = "라스 공방";
        String price = "1000원";
        String imageURL = "https://magical.dev/static/las.jpg";
        savedProduct = new Product(name, maker, price, imageURL);

        inMemoryProductRepository = new InMemoryProductRepository();
        inMemoryProductRepository.save(savedProduct);
    }

    @Test
    void findSavedProduct() {
        Optional<Product> foundProduct = inMemoryProductRepository.find(1L);
        assertThat(foundProduct).isNotEmpty();
        assertThat(foundProduct.get()).isEqualTo(savedProduct);
    }

    @Test
    void findNotSavedProduct() {
        Optional<Product> foundProduct = inMemoryProductRepository.find(-1L);
        assertThat(foundProduct).isEmpty();
    }

    @Test
    void findAll() {
        assertThat(inMemoryProductRepository.findAll()).hasSize(1).contains(savedProduct);
    }
}
