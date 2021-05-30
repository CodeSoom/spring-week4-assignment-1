package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("ProductRepository")
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    Product product;
    List<Product> products;

    Product saveProduct(Long index) {
        Product product = new Product();
        product.setName("Name" + index);
        product.setMaker("Maker " + index);
        product.setPrice(index * 1000L);
        product.setImageUrl("http://localhost:8080/fish" + index);
        return product;
    }


    @Nested
    @DisplayName("findAll() 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("고양이 장난감 목록이 존재하면")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS) //class당 인스턴스 생성
        class Context_not_empty_findAll {
            private Long productId = 1L;

            @BeforeEach
            void setUpNotEmptyFindAll() {
                product = saveProduct(productId);
            }

            @Test
            @DisplayName("장난감 목록을 반환한다. ")
            void existed_findAll_not_empty1() {
                products = productRepository.findAll();
                assertThat(products)
                        .isNotEmpty();
            }
        }
    }


    @Nested
    @DisplayName("findById() 메소드는")
    class Describe_findById {
        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 있다면")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS) //class당 인스턴스 생성
        class Context_existed_findById {
            private Long productId = 20L;
            private Optional<Product> returnProduct;
            private Product saveProduct;

            @BeforeEach
            void setUpExistedFindById() {
                //given
                product = saveProduct(productId);
                saveProduct = productRepository.save(product);
            }



            @Test
            @DisplayName("요청한 고양이 장난감을 반환한다. ")
            void existed_findById_return() {
                returnProduct = productRepository.findById(saveProduct.getId());
                assertThat(returnProduct.get().getId())
                        .isEqualTo(saveProduct.getId());
            }
        }

        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 없다면")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS) //class당 인스턴스 생성
        class Context_not_existed_findById {
            private Long productId = 100L;
            private Optional<Product> returnProduct;

            @Test
            @DisplayName("비어있는 고양이 장난감을 반환한다.")
            void not_existed_findById_exception() {
                returnProduct = productRepository.findById(productId);
                assertThat(returnProduct)
                        .isEmpty();
            }
        }
    }


    @Nested
    @DisplayName("등록된 고양이 장난감 목록이 없다면")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS) //class당 인스턴스 생성
    class Context_empty_findAll {
        private ProductRepository emptyProductRepository;

        @BeforeEach
        void setUpEmptyFindAll() {
            emptyProductRepository = mock(ProductRepository.class);
            given(emptyProductRepository.findAll()).willReturn(new ArrayList<>());
        }

        @Test
        @DisplayName("비어있는 목록을 반환한다.")
        void not_existed_findAll_empty() {
            products = emptyProductRepository.findAll();
            assertThat(products)
                    .isEmpty();
        }
    }


    @Nested
    @DisplayName("save() 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("성공적으로 고양이 장난감이 등록된다면")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS) //class당 인스턴스 생성
        class Context_valid_save {
            private Long productId = 30L;
            Product returnProduct;

            @BeforeAll
            void setUpValidSave() {
                product = saveProduct(productId);
            }

            @Test
            @DisplayName("등록한 고양이 장난감을 반환한다.")
            void valid_save_return() {
                returnProduct = productRepository.save(product);
                assertAll(
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("name")
                                    .isEqualTo(product.getName());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("maker")
                                    .isEqualTo(product.getMaker());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("price")
                                    .isEqualTo(product.getPrice());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("imageUrl")
                                    .isEqualTo(product.getImageUrl());
                        }
                );
            }
        }
    }

    @Nested
    @DisplayName("delete() 메소드는")
    class Describe_delete {
        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 있다면")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS) //class당 인스턴스 생성
        class Context_existed_delete {
            private Long productId = 40L;
            private Optional<Product> foundProduct;

            @BeforeAll
            void setUpExistedDelete() {
                product = saveProduct(productId);
                productRepository.save(product);
            }

            @Test
            @DisplayName("고양이 장난감을 삭제한다.")
            void existed_delete() {
                productRepository.delete(product);
                foundProduct = productRepository.findById(productId);
                assertThat(foundProduct)
                        .isNotNull()
                        .isEmpty();
            }
        }
    }
}
