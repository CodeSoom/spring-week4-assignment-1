package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    private ProductService productService;

    public static final String NAME = "털뭉치";
    public static final String MAKER = "애옹이네 장난감";
    public static final int PRICE = 2000;
    public static final String IMAGE_URL = "https://cdn.pixabay.com/photo/2018/10/05/12/09/animal-3725762_960_720.jpg";

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {
        Product product;

        @BeforeEach
        void setUp() {
            product = new Product(NAME, MAKER, PRICE, IMAGE_URL);
        }

        @Test
        @DisplayName("생성된 product를 반환한다.")
        void it_returns_created_product() {
            Product newProduct = productService.createTask(product);

            assertThat(newProduct.getId()).isNotNull();
            assertThat(newProduct.getName()).isEqualTo(NAME);
            assertThat(newProduct.getMaker()).isEqualTo(MAKER);
            assertThat(newProduct.getPrice()).isEqualTo(PRICE);
            assertThat(newProduct.getImageUrl()).isEqualTo(IMAGE_URL);
        }
    }

}
