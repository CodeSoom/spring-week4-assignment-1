package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductCommandDto;
import com.codesoom.assignment.dto.ProductDto;
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
        ProductCommandDto productCommandDto;

        @BeforeEach
        void setUp() {
            productCommandDto = ProductCommandDto.builder()
                    .name(NAME)
                    .maker(MAKER)
                    .price(PRICE)
                    .imageUrl(IMAGE_URL).build();
        }

        @Test
        @DisplayName("생성된 product의 dto를 반환한다.")
        void it_returns_created_product() {
            ProductDto newProduct = productService.createTask(productCommandDto);

            assertThat(newProduct.getId()).isNotNull();
            assertThat(newProduct.getName()).isEqualTo(NAME);
            assertThat(newProduct.getMaker()).isEqualTo(MAKER);
            assertThat(newProduct.getPrice()).isEqualTo(PRICE);
            assertThat(newProduct.getImageUrl()).isEqualTo(IMAGE_URL);
        }
    }
}
