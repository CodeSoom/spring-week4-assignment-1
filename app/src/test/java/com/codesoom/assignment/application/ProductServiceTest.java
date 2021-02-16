package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFountException;
import com.codesoom.assignment.domain.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("ProductService 클래스")
class ProductServiceTest {
    final Long NOT_EXIST_ID = 100L;
    final String NAME = "My Toy";
    final String MAKER = "My Home";
    final Long PRICE = 5000L;
    final String IMAGE_URL = "https://cdn.pixabay.com/photo/2016/10/01/20/54/mouse-1708347_1280.jpg";

    final String UPDATE_NAME = "My Toy";
    final String UPDATE_MAKER = "My Home";
    final Long UPDATE_PRICE = 5000L;
    final String UPDATE_IMAGE_URL = "https://cdn.pixabay.com/photo/2016/10/01/20/54/mouse-1708347_1280.jpg";

    @Autowired
    ProductService productService;

    @BeforeEach
    void setUp() {
        productService.clearData();
    }

    //subject
    Product createProduct() {
        Product product = new Product();
        product.setName(NAME);
        product.setMaker(MAKER);
        product.setPrice(PRICE);
        product.setImageURL(IMAGE_URL);
        return productService.save(product);
    }

    //subject
    void verifyFindProduct(Product product) {
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImageURL()).isEqualTo(IMAGE_URL);
    }

    @Nested
    @DisplayName("create()")
    class Describe_create {
        Product givenProduct;

        @BeforeEach
        void setUp() {
            givenProduct = createProduct();
        }

        @DisplayName("생성된 product를 반환한다")
        @Test
        void it_returns_created_product() {
            verifyFindProduct(givenProduct);
        }
    }

    @Nested
    @DisplayName("find()")
    class Describe_find {
        @Nested
        @DisplayName("존재하는 product id가 주어진다면")
        class Context_exist_product_id {
            Long givenProductId;

            @BeforeEach
            void setUp() {
                Product givenProduct = createProduct();
                givenProductId = givenProduct.getId();
            }

            @DisplayName("주어진 id와 일치하는 product를 반환한다")
            @Test
            void it_returns_product() {
                Product product = productService.find(givenProductId);
                verifyFindProduct(product);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 product id가 주어진다면")
        class Context_not_exist_product_id {
            Long givenProductId = NOT_EXIST_ID;

            @DisplayName("product를 찾을 수 없다는 예외를 던진다")
            @Test
            void it_returns_exception() {
                assertThrows(ProductNotFountException.class, () -> productService.find(givenProductId));
            }
        }
    }

    @Nested
    @DisplayName("findAll()")
    class Describe_findAll {
        @Nested
        @DisplayName("product가 존재한다면")
        class Context_product_exist {
            @BeforeEach
            void setUp() {
                createProduct();
            }

            @Test
            @DisplayName("product 리스트를 반환한다")
            void it_return_product_list() {
                List<Product> list = productService.findAll();
                assertThat(list).isNotEmpty();
            }
        }

        @Nested
        @DisplayName("product가 존재하지 않는다면")
        class Context_product_not_exist {

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void it_return_empty_list() {
                List<Product> list = productService.findAll();
                assertThat(list).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("update()")
    class Describe_update {
        @Nested
        @DisplayName("존재하는 product id와 수정한 product가 주어진다면")
        class Context_exist_product_id {
            Long givenProductId;
            Product source;

            @BeforeEach
            void setUp() {
                Product givenProduct = createProduct();
                givenProductId = givenProduct.getId();

                source = new Product();
                source.setName(UPDATE_NAME);
                source.setMaker(UPDATE_MAKER);
                source.setPrice(UPDATE_PRICE);
                source.setImageURL(UPDATE_IMAGE_URL);
            }

            @DisplayName("수정된 product를 반환한다")
            @Test
            void it_returns_product() {
                Product product = productService.update(givenProductId, source);

                assertThat(product.getId()).isEqualTo(givenProductId);
                assertThat(product.getName()).isEqualTo(UPDATE_NAME);
                assertThat(product.getMaker()).isEqualTo(UPDATE_MAKER);
                assertThat(product.getPrice()).isEqualTo(UPDATE_PRICE);
                assertThat(product.getImageURL()).isEqualTo(UPDATE_IMAGE_URL);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 product id와 수정한 product가 주어진다면")
        class Context_not_exist_product_id {
            Long givenProductId;
            Product source;

            @BeforeEach
            void setUp() {
                givenProductId = NOT_EXIST_ID;
                source = new Product();
                source.setName(UPDATE_NAME);
                source.setMaker(UPDATE_MAKER);
                source.setPrice(UPDATE_PRICE);
                source.setImageURL(UPDATE_IMAGE_URL);
            }

            @DisplayName("product를 찾을 수 없다는 예외를 던진다")
            @Test
            void it_returns_exception() {
                assertThrows(ProductNotFountException.class, () -> productService.update(NOT_EXIST_ID, source));
            }
        }
    }


    @Nested
    @DisplayName("delete()")
    class Describe_delete {
        @Nested
        @DisplayName("존재하는 product id가 주어진다면")
        class Context_exist_product_id {
            Long givenProductId;

            @BeforeEach
            void setUp() {
                Product givenProduct = createProduct();
                givenProductId = givenProduct.getId();
            }

            @DisplayName("주어진 id와 일치하는 product를 삭제한다")
            @Test
            void it_delete_product() {
                productService.delete(givenProductId);
                assertThrows(ProductNotFountException.class, () -> productService.find(givenProductId));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 product id가 주어진다면")
        class Context_not_exist_product_id {
            Long givenProductId = NOT_EXIST_ID;

            @DisplayName("product를 찾을 수 없다는 예외를 던진다")
            @Test
            void it_returns_exception() {
                assertThrows(ProductNotFountException.class, () -> productService.delete(givenProductId));
            }
        }
    }
}
