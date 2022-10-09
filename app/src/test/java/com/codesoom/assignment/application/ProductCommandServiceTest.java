package com.codesoom.assignment.application;

import com.codesoom.assignment.controller.dto.ProductRequestDto;
import com.codesoom.assignment.controller.dto.ProductResponseDto;
import com.codesoom.assignment.controller.dto.ProductUpdateRequest;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("ProductCommandService 클래스")
class ProductCommandServiceTest {

    private final String name = "My toy";
    private final String maker = "toy shop";
    private final int price = 15000;
    private final String imageUrl = "toy.jpg";

    @Autowired
    private ProductRepository productRepository;

    private ProductCommandService productCommandService;

    @BeforeEach
    void setUp() {
        productCommandService = new ProductCommandService(productRepository);
    }

    @AfterEach
    void clear() {
        productRepository.deleteAll();
    }

    private ProductUpdateRequest getProductRequest() {
        return new ProductRequestDto(name, maker, price, imageUrl);
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {

        @Test
        @DisplayName("상품을 생성해 반환한다")
        void it_returns_new_product() {
            ProductResponseDto createdProduct = productCommandService.create(getProductRequest());

            assertThat(createdProduct).isNotNull();
            assertThat(createdProduct.getId()).isNotNull();
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("수정이 가능한 경우")
        class Context_with_valid_id {
            private Long id;
            private ProductUpdateRequest source;

            @BeforeEach
            void setUp() {
                ProductResponseDto product = productCommandService.create(getProductRequest());
                id = product.getId();

                final String updatePrefix = "updated_";
                final int updatedPrice = 20000;

                source = new ProductRequestDto(updatePrefix + name,
                        updatePrefix + maker,
                        updatedPrice,
                        updatePrefix + imageUrl);
            }

            @Test
            @DisplayName("수정된 상품을 반환한다")
            void it_returns_updated_product() {
                ProductResponseDto updatedProduct = productCommandService.update(id, source);

                assertThat(updatedProduct).isNotNull();
                assertThat(updatedProduct.getId()).isEqualTo(id);
                assertThat(updatedProduct.getName()).isEqualTo(source.getName());
                assertThat(updatedProduct.getMaker()).isEqualTo(source.getMaker());
                assertThat(updatedProduct.getPrice()).isEqualTo(source.getPrice());
                assertThat(updatedProduct.getImageUrl()).isEqualTo(source.getImageUrl());
            }
        }

        @Nested
        @DisplayName("수정이 불가능한 경우")
        class Context_with_invalid_id {
            private Long invalidId;

            @BeforeEach
            void setUp() {
                ProductResponseDto product = productCommandService.create(getProductRequest());
                invalidId = product.getId();

                productCommandService.deleteById(invalidId);
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productCommandService.update(invalidId, getProductRequest()))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_deleteById {

        @Nested
        @DisplayName("삭제 가능한 id가 주어지면")
        class Context_with_valid_id {
            private Long id;

            @BeforeEach
            void setUp() {
                ProductResponseDto product = productCommandService.create(getProductRequest());
                id = product.getId();
            }

            @Test
            @DisplayName("삭제를 수행한다")
            void it_removes_product() {
                productCommandService.deleteById(id);
            }
        }

        @Nested
        @DisplayName("삭제 불가능한 id가 주어지면")
        class Context_with_invalid_id {
            private Long invalidId;

            @BeforeEach
            void setUp() {
                ProductResponseDto product = productCommandService.create(getProductRequest());
                invalidId = product.getId();

                productCommandService.deleteById(invalidId);
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productCommandService.deleteById(invalidId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteByIds 메소드는")
    class Describe_deleteByIds {

        @Nested
        @DisplayName("삭제 가능한 id 목록이 주어지면")
        class Context_with_valid_id {
            private final int idSize = 3;
            private final Set<Long> ids = new HashSet<>(idSize);

            @BeforeEach
            void setUp() {
                for (int i = 0; i < idSize; i++) {
                    ProductResponseDto product = productCommandService.create(getProductRequest());
                    ids.add(product.getId());
                }
            }

            @Test
            @DisplayName("삭제를 수행한다")
            void it_removes_product() {
                productCommandService.deleteByIds(ids);
            }
        }

        @Nested
        @DisplayName("id 목록중에 삭제 불가능한 id가 하나라도 주어지면")
        class Context_with_invalid_id {
            private final int idSize = 3;
            private final Set<Long> ids = new HashSet<>(idSize);

            @BeforeEach
            void setUp() {
                for (int i = 0; i < idSize; i++) {
                    ProductResponseDto product = productCommandService.create(getProductRequest());
                    ids.add(product.getId());
                }

                productCommandService.deleteById(ids.iterator().next());
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productCommandService.deleteByIds(ids))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
