package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
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

    Product product;
    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
        product = new Product(NAME, MAKER, PRICE, IMAGE_URL);
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

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {
        private static final String UPDATE_NAME = "캣닢";
        private static final String UPDATE_MAKER = "캣드러그";
        private static final int UPDATE_PRICE = 7000;
        private static final String UPDATE_IMAGE_URL = "https://www.catdrug.com/images/1234567";

        private Long id;
        private ProductCommandDto productCommandDto;

        @BeforeEach
        void setUp() {
            productCommandDto = ProductCommandDto.builder()
                    .name(UPDATE_NAME)
                    .maker(UPDATE_MAKER)
                    .price(UPDATE_PRICE)
                    .imageUrl(UPDATE_IMAGE_URL)
                    .build();
        }

        @DataJpaTest
        @Nested
        @DisplayName("존재하는 id가 주어지면")
        class Context_with_existed_id {

            @BeforeEach
            void setUp() {
                Product newProduct = productRepository.save(product);
                id = newProduct.getId();
            }

            @Test
            @DisplayName("product를 변경한다.")
            void it_updates_product() {
                productService.updateProduct(id, productCommandDto);

                Product updatedProduct = productRepository.findById(id).get();

                assertThat(updatedProduct.getName()).isEqualTo(UPDATE_NAME);
                assertThat(updatedProduct.getMaker()).isEqualTo(UPDATE_MAKER);
                assertThat(updatedProduct.getPrice()).isEqualTo(UPDATE_PRICE);
                assertThat(updatedProduct.getImageUrl()).isEqualTo(UPDATE_IMAGE_URL);
            }
        }
    }
}
