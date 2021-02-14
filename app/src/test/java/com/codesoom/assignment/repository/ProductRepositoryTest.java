package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void prepareProduct() {
        // fixture
        product = new Product("장난감", "장난감 메이커", 10000, "url");
        productRepository.save(product);
    }

    @Test
    void saveProduct() {
        Product newProduct = new Product("장난감", "장난감 메이커", 10000, "url");

        productRepository.save(newProduct);

        assertThat(productRepository.findAll()).hasSize(2);
    }

    @Test
    void findProducts() {
        Product product1 = new Product("장난감1", "장난감 메이커", 10000, "url");
        Product product2 = new Product("장난감2", "장난감 메이커", 10000, "url");
        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> products = productRepository.findAll();

        assertThat(products).hasSize(3);
    }

    @Test
    void findProduct() {
        Optional<Product> foundProduct = productRepository.findById(product.getId());

        assertThat(foundProduct.isPresent()).isTrue();
    }

    @Test
    void deleteProduct() {
        productRepository.delete(product);

        assertThat(productRepository.findAll()).hasSize(0);
    }

}
