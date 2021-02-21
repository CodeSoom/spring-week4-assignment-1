package com.codesoom.assignment.domain;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ProductRepository 클래스")
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    private final String SETUP_PRODUCT_NAME = "setupName";
    private final String SETUP_PRODUCT_MAKER = "setupMaker";
    private final int SETUP_PRODUCT_PRICE = 100;
    private final String SETUP_PRODUCT_IMAGEURL = "setupImage";

    private final String CREATE_PRODUCT_NAME = "createName";
    private final String CREATE_PRODUCT_MAKER = "createMaker";
    private final int CREATE_PRODUCT_PRICE = 200;
    private final String CREATE_PRODUCT_IMAGEURL = "createImage";

    private Product setUpProduct;


    @BeforeEach
    void setUp() {
        setUpProduct = Product.builder()
                .name(SETUP_PRODUCT_NAME)
                .maker(SETUP_PRODUCT_MAKER)
                .price(SETUP_PRODUCT_PRICE)
                .imageUrl(SETUP_PRODUCT_IMAGEURL)
                .build();
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        private Long givenExistedId;

        @BeforeEach
        void setup() {
            givenExistedId = productRepository.save(setUpProduct).getId();
        }

        @Test
        @DisplayName("저장되어 있는 고양이 장난감 전체 목록을 리턴한다")
        void itReturnsAllOfProducts() {
            List<Product> products = productRepository.findAll();
            Product product = products.get(products.size()-1);

            assertThat(product.getId())
                    .as("목록의 마지막 객체는 아이디가 %f 이어야 한다", givenExistedId)
                    .isEqualTo(givenExistedId);
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감 아이디가 주어진다면")
        class Context_WithExistedId {
            private Long givenExistedId;

            @BeforeEach
            void setUp() {
                givenExistedId = productRepository.save(setUpProduct).getId();
            }

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감을 리턴한다")
            void itReturnsProduct() {
                Product product = productRepository.findById(givenExistedId).get();
                assertThat(product.getId()).isEqualTo(givenExistedId);
            }
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {
        @Nested
        @DisplayName("만약 고양이 장난감 객체가 주어진다면")
        class Context_WithProduct {
            private final Product givenProduct = Product.builder()
                    .name(CREATE_PRODUCT_NAME)
                    .maker(CREATE_PRODUCT_MAKER)
                    .price(CREATE_PRODUCT_PRICE)
                    .imageUrl(CREATE_PRODUCT_IMAGEURL)
                    .build();

            @Test
            @DisplayName("주어진 객체를 저장하고 저장된 객체를 리턴한다")
            void itSavesObjectAndReturnsSavedObject() {
                assertThat(givenProduct.getId())
                        .as("저장되지 않은 고양이 장난감 객체는 아이디가 null 이다")
                        .isNull();

                int oldSize = productRepository.findAll().size();

                Product savedProduct = productRepository.save(givenProduct);

                assertThat(savedProduct.getId())
                        .as("저장되어 있는 고양이 장난감 객체는 아이디가 null이 아니다")
                        .isNotNull();

                assertThat(productRepository.findAll().size())
                        .as("고양이 장난감 객체를 저장하면 전체 목록 사이즈가 1 증가한다")
                        .isEqualTo(oldSize + 1);

                assertThat(savedProduct.getName()).isEqualTo(CREATE_PRODUCT_NAME);
                assertThat(savedProduct.getMaker()).isEqualTo(CREATE_PRODUCT_MAKER);
                assertThat(savedProduct.getPrice()).isEqualTo(CREATE_PRODUCT_PRICE);
                assertThat(savedProduct.getImageUrl()).isEqualTo(CREATE_PRODUCT_IMAGEURL);
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감 아이디가 주어진다면")
        class Context_WithExistedId {
            private Long givenExistedId;

            @BeforeEach
            void setUp() {
                givenExistedId = productRepository.save(setUpProduct).getId();
            }

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감 객체를 삭제한다")
            void itDeleteObject() {
                Product deleteProduct = productRepository.findById(givenExistedId).get();
                assertThat(deleteProduct)
                        .as("삭제되지 않은 고양이 장난감 객체는 null이 아니다")
                        .isNotNull();
                
                int oldSize = productRepository.findAll().size();

                productRepository.delete(deleteProduct);

                Product deletedProduct = productRepository.findById(givenExistedId).orElse(null);
                assertThat(deletedProduct)
                        .as("삭제된 고양이 장난감 객체는 null이다")
                        .isNull();

                assertThat(productRepository.findAll().size())
                        .as("고양이 장난감 객체를 삭제하면 전체 목록 사이즈가 1 감소한다")
                        .isEqualTo(oldSize -1 );
            }
        }
    }
}