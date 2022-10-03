package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DisplayName("ProductRepository 테스트")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private final String name = "장난감";
    private final String maker = "장난감샵";
    private final int price = 15000;
    private final String imageUrl = "toy.jpg";

    @Test
    @DisplayName("save 메소드는 장난감을 저장하고 반환한다")
    void save_test() {
        Product product = new Product(name, maker, price, imageUrl);

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getName()).isEqualTo(name);
        assertThat(savedProduct.getMaker()).isEqualTo(maker);
        assertThat(savedProduct.getPrice()).isEqualTo(price);
        assertThat(savedProduct.getImageUrl()).isEqualTo(imageUrl);
    }
}
