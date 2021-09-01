package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@DisplayName("ProductServiceTest 클래스")
class ProductServiceImplTest {

    private Long[] PRODUCT_ID = {1L, 2L, 3L, 4L, 5L};
    private String[] PRODUCT_NAME = {"test1", "test2", "test3", "test4", "test5",};
    private String[] PRODUCT_MAKER = {"maker1", "maker2", "maker3", "maker4", "maker5"};
    private Long[] PRODUCT_PRICE = {1000L, 2000L, 3000L, 4000L, 5000L};
    private String[] PRODUCT_IMG = {"img1", "img2", "img3", "img4", "img5"};

    private  ProductRepository productRepository;
    private  ProductService productService;

    @BeforeEach
    void setUp() {

        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);

    }

    @Nested
    @DisplayName("register 메소드는")
    class Describe_register {

        @Nested
        @DisplayName("등록할 장난감이 있다면")
        class Context_exist_product {

            Product product;

            @BeforeEach
            void setUp() {

                product = new Product();
                product.setId(PRODUCT_ID[0]);
                product.setName(PRODUCT_NAME[0]);
                product.setMaker(PRODUCT_MAKER[0]);
                product.setPrice(PRODUCT_PRICE[0]);
                product.setImg(PRODUCT_IMG[0]);

                productService.register(product);
                given(productService.register(any(Product.class))).willReturn(product);

            }

            @Test
            @DisplayName("디비에 장난감을 저장한다")
            void It_save_product() {

                verify(productRepository).save(product);

            }

        }

    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {

        @Nested
        @DisplayName("디비에 아이디가 존재하면")
        class Context_exist_id {

            Product product;

            @BeforeEach
            void setUp() {

                product = new Product();
                product.setId(PRODUCT_ID[0]);
                product.setName(PRODUCT_NAME[0]);
                product.setMaker(PRODUCT_MAKER[0]);
                product.setPrice(PRODUCT_PRICE[0]);
                product.setImg(PRODUCT_IMG[0]);

                productService.register(product);
                given(productService.getProduct(PRODUCT_ID[0])).willReturn(Optional.of(product));

            }

            @Test
            @DisplayName("product 객체를 리턴한다")
            void It_return_product() {

                assertThat(productService.getProduct(PRODUCT_ID[0]).get()).isEqualTo(product);
                verify(productRepository).findById(PRODUCT_ID[0]);

            }

        }

    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {

        @Nested
        @DisplayName("디비에 장난감들이 존재하면")
        class Context_exist_products {

            List<Product> products;

            @BeforeEach
            void setUp() {

                products = new ArrayList<>();

                for (int i = 0; i < PRODUCT_ID.length; i++) {

                    Product product = new Product();
                    product.setId(PRODUCT_ID[i]);
                    product.setName(PRODUCT_NAME[i]);
                    product.setMaker(PRODUCT_MAKER[i]);
                    product.setPrice(PRODUCT_PRICE[i]);
                    product.setImg(PRODUCT_IMG[i]);
                    products.add(product);

                }

                given(productService.getProducts()).willReturn(products);

            }

            @Test
            @DisplayName("장난감 들을 리턴한다")
            void It_return_products() {

                assertThat(productService.getProducts()).hasSize(PRODUCT_ID.length);
                verify(productRepository).findAll();

            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

        @Nested
        @DisplayName("삭제할 아이디가 존재하면")
        class Context_exist_delete_id {

            List<Product> products;

            @BeforeEach
            void setUp() {

                products = new ArrayList<>();

                for (int i = 0; i < PRODUCT_ID.length; i++) {

                    Product product = new Product();
                    product.setId(PRODUCT_ID[i]);
                    product.setName(PRODUCT_NAME[i]);
                    product.setMaker(PRODUCT_MAKER[i]);
                    product.setPrice(PRODUCT_PRICE[i]);
                    product.setImg(PRODUCT_IMG[i]);
                    products.add(product);

                }

                products.remove(0);
                productService.delete(PRODUCT_ID[0]);

            }

            @Test
            @DisplayName("디비에서 해당 아이디 객체를 삭제한다")
            void It_delete_product() {

                assertThat(products).hasSize(PRODUCT_ID.length - 1);
                verify(productRepository).deleteById(PRODUCT_ID[0]);

            }

        }

    }

}
