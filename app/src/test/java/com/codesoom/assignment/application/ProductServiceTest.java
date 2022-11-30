package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductService 클래스 테스트")
class ProductServiceTest {

    private ProductServiceImpl service;
    private ProductRepository repository;

    private static final String PRODUCT_NAME = "춘식이 고구마 장난감";
    private static final String MAKER        = "카카오";
    private static final Long PRICE          = 39000L;
    private static final String IMAGE_URL    = "http://localhost:8080/snake";

    @BeforeEach
    void setUp() {
        //TODO 왜 mock로 감싸야하는지 강의 다시보기!
        repository = mock(ProductRepository.class);
        service = new ProductServiceImpl(repository);

        List<Product> products = new ArrayList<>();
        Product product = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);
        products.add(product);

        given(service.findAll()).willReturn(products);
        given(service.findById(1L)).willReturn(Optional.of(product));
        given(service.findById(100L)).willReturn(Optional.empty());
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("id가 존재하지 않으면")
        class Context_without_id {
            @Test
            @DisplayName("product를 리포지토리에 저장한다")
            void it_save_product() {
                Product product = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);

                service.save(product);

                verify(repository).save(product);

                assertThat(service.findById(1L)).isNotNull();
                assertThat(service.findById(1L)
                                    .get()
                                    .getProductName())
                                    .isEqualTo(PRODUCT_NAME);
            }
        }

        @Nested
        @DisplayName("id가 존재하면")
        class Context_with_id {
            @Test
            @DisplayName("id에 해당하는 product를 업데이트한다")
            void it_update_product() {
                Product product = service.findById(1L)
                                            .stream()
                                            .findFirst()
                                            .orElseThrow(ProductNotFoundException::new);

                product.setProductName("춘식이 감자 장난감");
                product.setPrice(10000L);

                service.save(product);

                verify(repository).save(product);

                assertThat(service.findAll().get(0).getProductName())
                        .isEqualTo("춘식이 감자 장난감");
                assertThat(service.findAll().get(0).getPrice())
                        .isEqualTo(10000L);
            }
        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        @BeforeEach
        void setUp() {
            Product product = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);

            service.save(product);
        }

        @Test
        @DisplayName("모든 Product 리스트를 리턴한다")
        void it_return_products() {
            List<Product> products = service.findAll();

            assertThat(products.size()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
        @Nested
        @DisplayName("id가 존재할 때")
        class Context_with_validId {
            @Test
            @DisplayName("해당하는 id의 Product를 리턴한다")
            void it_return_product() {
                Product product = service.findById(1L)
                                            .stream()
                                            .findFirst()
                                            .orElseThrow(ProductNotFoundException::new);

                assertThat(product.getProductName()).isEqualTo(PRODUCT_NAME);
                assertThat(product.getMaker()).isEqualTo(MAKER);
                assertThat(product.getPrice()).isEqualTo(PRICE);
                assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않을 때")
        class Context_with_invalidId {
            @Test
            @DisplayName("empty를 리턴한다")
            void it_return_empty() {
                assertThat(service.findById(100L)).isEmpty();

                verify(repository).findById(100L);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_deleteById {
        @Test
        @DisplayName("id에 해당하는 product를 삭제한다")
        void it_remove_product() {
            service.deleteById(1L);

            verify(repository).deleteById(1L);
        }
    }
}
