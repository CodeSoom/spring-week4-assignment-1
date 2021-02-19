package com.codesoom.assignment.domain;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    private final String SETUP_PRODUCT_IMAGE = "setupImage";

    private final String CREATE_PRODUCT_NAME = "createName";
    private final String CREATE_PRODUCT_MAKER = "createMaker";
    private final int CREATE_PRODUCT_PRICE = 200;
    private final String CREATE_PRODUCT_IMAGE = "createImage";

    private Product setUpProduct;

    @BeforeEach
    void setUp() {
        setUpProduct = Product.builder()
                .name(SETUP_PRODUCT_NAME)
                .maker(SETUP_PRODUCT_MAKER)
                .price(SETUP_PRODUCT_PRICE)
                .image(SETUP_PRODUCT_IMAGE)
                .build();
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        @BeforeEach
        void setUp() {
            productRepository.save(setUpProduct);
        }

        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감 아이디가 주어진다면")
        class Context_WithExistedId {
            private Long givenExistedId;

            @BeforeEach
            void setUp() {
                List<Product> list = productRepository.findAll();
                givenExistedId = list.get(list.size()-1).getId();
            }

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감을 리턴한다")
            void itReturnsProduct() {
                Product product = productRepository.findById(givenExistedId).get();
                assertThat(product.getId()).isEqualTo(givenExistedId);
                assertThat(product.getName()).isEqualTo(SETUP_PRODUCT_NAME);
                assertThat(product.getMaker()).isEqualTo(SETUP_PRODUCT_MAKER);
                assertThat(product.getPrice()).isEqualTo(SETUP_PRODUCT_PRICE);
                assertThat(product.getImage()).isEqualTo(SETUP_PRODUCT_IMAGE);
            }
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {
        @Nested
        @DisplayName("만약 장난감 고양이 객체가 주어진다면")
        class Context_WithProduct {
            private final Product givenProduct = Product.builder()
                    .name(CREATE_PRODUCT_NAME)
                    .maker(CREATE_PRODUCT_MAKER)
                    .price(CREATE_PRODUCT_PRICE)
                    .image(CREATE_PRODUCT_IMAGE)
                    .build();

            @Test
            @DisplayName("주어진 객체를 저장하고 저장된 객체를 리턴한다")
            void itSavesObjectAndReturnsSavedObject() {
                assertThat(givenProduct.getId())
                        .as("저장되지 않은 장난감 고양이 객체는 아이디가 null 이다")
                        .isNull();

                Product savedProduct = productRepository.save(givenProduct);

                assertThat(savedProduct.getId())
                        .as("저장되어 있는 장난감 고양이 객체는 아이디가 null이 아니다")
                        .isNotNull();
                assertThat(savedProduct.getName()).isEqualTo(CREATE_PRODUCT_NAME);
                assertThat(savedProduct.getMaker()).isEqualTo(CREATE_PRODUCT_MAKER);
                assertThat(savedProduct.getPrice()).isEqualTo(CREATE_PRODUCT_PRICE);
                assertThat(savedProduct.getImage()).isEqualTo(CREATE_PRODUCT_IMAGE);
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {
        @BeforeEach
        void setUp() {
            productRepository.save(setUpProduct);
        }

        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감 아이디가 주어진다면")
        class Context_WithExistedId {
            private Long givenExistedId;

            @BeforeEach
            void setUp() {
                List<Product> list = productRepository.findAll();
                givenExistedId = list.get(list.size()-1).getId();
            }

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감 객체를 삭제한다")
            void itDeleteObject() {
                System.out.println(givenExistedId);
                System.out.println(givenExistedId);
                Product deleteProduct = productRepository.findById(givenExistedId).get();
                assertThat(deleteProduct)
                        .as("삭제되지 않은 고양이 장난감 객체는 null이 아니다")
                        .isNotNull();

                productRepository.delete(deleteProduct);

                Product deletedProduct = productRepository.findById(givenExistedId).orElse(null);
                assertThat(deletedProduct)
                        .as("삭제된 고양이 장난감 객체는 null이다")
                        .isNull();
            }
        }
    }
}