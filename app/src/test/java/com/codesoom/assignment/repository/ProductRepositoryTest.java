package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("ProductRepository 클래스")
class ProductRepositoryTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);

    private Long[] PRODUCT_ID = {1L, 2L, 3L, 4L, 5L};
    private String[] PRODUCT_NAME = {"test1", "test2", "test3", "test4", "test5",};
    private String[] PRODUCT_MAKER = {"maker1", "maker2", "maker3", "maker4", "maker5"};
    private Long[] PRODUCT_PRICE = {1000L, 2000L, 3000L, 4000L, 5000L};
    private String[] PRODUCT_IMG = {"img1", "img2", "img3", "img4", "img5"};

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {

        @Nested
        @DisplayName("게시할 장난감이 있다면")
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

                productRepository.save(product);

                given(productRepository.save(product)).will(invocation -> {
                    return product;
                });

            }

            @Test
            @DisplayName("DB에 객체를 저장한다.")
            void It_save_product() {

                assertThat(product.getName()).isEqualTo(PRODUCT_NAME[0]);
                Mockito.verify(productRepository).save(product);

            }

        }

    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findALl {

        @Nested
        @DisplayName("디비에 장난감 판매 목록이 있다면")
        class Context_exist_products {

            @BeforeEach
            void setUp() {

                List<Product> products = new ArrayList<>();

                for (int i = 0; i < PRODUCT_ID.length; i++) {

                    Product product = new Product();
                    product.setId(PRODUCT_ID[i]);
                    product.setName(PRODUCT_NAME[i]);
                    product.setMaker(PRODUCT_MAKER[i]);
                    product.setPrice(PRODUCT_PRICE[i]);
                    product.setImg(PRODUCT_IMG[i]);

                    products.add(product);

                }

                given(productRepository.findAll()).willReturn(products);

            }

            @Test
            @DisplayName("장난감 판매 리스트를 리턴한다")
            void It_return_products() {

                assertThat(productRepository.findAll()).isNotEmpty();
                assertThat(productRepository.findAll()).hasSize(PRODUCT_ID.length);

            }

        }

        @Nested
        @DisplayName("디비에 장난감 목록이 없다면")
        class Context_exist_not_products {

            List<Product> products;

            @BeforeEach
            void setUp() {
                products = productRepository.findAll();
            }

            @Test
            @DisplayName("비어잇는 리스트를 리턴한다")
            void It_return_empty_products() {
                assertThat(products).isEmpty();
            }

        }

    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {

        @Nested
        @DisplayName("디비에 검색한 id의 product가 존재하면")
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

                productRepository.save(product);


                given(productRepository.findById(PRODUCT_ID[0])).willReturn(Optional.of(product));

            }

            @Test
            @DisplayName("id에 맞는 product 객체를 리턴한다")
            void It_return_product() {

                assertThat(PRODUCT_NAME[0]).isEqualTo(productRepository.findById(PRODUCT_ID[0]).get().getName());

            }

        }

    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_deleteById {

        @Nested
        @DisplayName("삭제할 id가 존재하면")
        class Context_exist_deleteId {

            @BeforeEach
            void setUp() {

                List<Product> products = new ArrayList<>();

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
                given(productRepository.findAll()).willReturn(products);

            }

            @Test
            @DisplayName("디비에서 product 객체를 삭제한다")
            void It_delete_product() {

                assertThat(productRepository.findAll()).hasSize(PRODUCT_ID.length-1);

            }

        }

    }

}
