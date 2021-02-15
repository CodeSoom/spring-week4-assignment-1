package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFountException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@DisplayName("ProductService 클래스")
class ProductServiceTest {
    final Long ID = 0L;
    final Long NOT_EXIST_ID = 100L;
    final String NAME = "My Toy";
    final String MAKER = "My Home";
    final Long PRICE = 5000L;
    final String IMAGE_URL = "https://cdn.pixabay.com/photo/2016/10/01/20/54/mouse-1708347_1280.jpg";

    @Autowired
    ProductService productService;
    @MockBean
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(productRepository);
        setUpCreateProduct();
        setUpFindNotExistProduct();
    }

    void setUpCreateProduct() {
        given(productRepository.save(any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(ID);
            return product;
        });
    }

    void setUpFindNotExistProduct() {
        given(productRepository.findById(NOT_EXIST_ID)).willReturn(Optional.empty());
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
    void verifyProduct(Product product) {
        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImageURL()).isEqualTo(IMAGE_URL);
    }

    @Nested
    @DisplayName("create()")
    class Describe_create {
        @DisplayName("생성된 product를 반환한다")
        @Test
        void it_returns_created_product() {
            //when
            Product product = createProduct();
            //then
            verifyProduct(product);
        }
    }

    @Nested
    @DisplayName("find(id)")
    class Describe_find {
        @Nested
        @DisplayName("존재하는 product id가 주어진다면")
        class Context_exist_product_id {
            Product givenProduct;

            @BeforeEach
            void setUp() {
                givenProduct = createProduct();
                given(productRepository.findById(ID)).willReturn(Optional.of(givenProduct));
            }

            @DisplayName("주어진 id와 일치하는 product를 반환한다")
            @Test
            void it_returns_product() {
                //when
                Product product = productService.find(givenProduct.getId());
                //then
                verifyProduct(product);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 product id가 주어진다면")
        class Context_not_exist_product_id {
            @DisplayName("product를 찾을 수 없다는 예외를 던진다")
            @Test
            void it_returns_exception() {
                //when
                //then
                assertThrows(ProductNotFountException.class, () -> productService.find(NOT_EXIST_ID));
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
                Product givenProduct = createProduct();
                given(productRepository.findAll()).willReturn(Collections.singletonList(givenProduct));
            }

            @Test
            @DisplayName("product 리스트를 반환한다")
            void it_return_product_list() {
                //when
                List<Product> list = productService.findAll();
                //then
                assertThat(list).isNotEmpty();
            }
        }

        @Nested
        @DisplayName("product가 존재하지 않는다면")
        class Context_product_not_exist {
            @BeforeEach
            void setUp() {
                given(productRepository.findAll()).willReturn(Collections.emptyList());
            }

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void it_return_empty_list() {
                //when
                List<Product> list = productService.findAll();
                //then
                assertThat(list).isEmpty();
            }
        }
    }
}
