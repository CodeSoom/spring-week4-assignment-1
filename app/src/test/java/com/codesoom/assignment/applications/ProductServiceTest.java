package com.codesoom.assignment.applications;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;

@DisplayName("ProductServiceTest 클래스")
class ProductServiceTest {
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        product1 = new Product(1L, "toy1", "maker1", 1000L, "toy1.jpg");
        product2 = new Product(2L, "toy2", "maker2", 2000L, "toy2.jpg");

        productService = new ProductService(productRepository);
    }

    @Nested
    @DisplayName("save 메소드")
    class Describe_save {

        @BeforeEach
        void setUp() {
            given(productRepository.save(product1)).willReturn(product1);
        }

        @Test
        @DisplayName("새로운 product를 반환합니다.")
        void it_return_new_product() {
            assertThat(productService.save(product1)).isEqualTo(product1);
        }
    }

    @Nested
    @DisplayName("findAll 메소드")
    class Describe_findAll {

        private List<Product> givenProducts;

        @BeforeEach
        void setUp() {
            givenProducts = List.of(product1, product2);

            given(productRepository.findAll()).willReturn(givenProducts);
        }

        @Test
        @DisplayName("모든 product를 반환합니다.")
        void it_return_all_product() {
            assertThat(productService.findAll()).isEqualTo(givenProducts);
        }
    }

    @Nested
    @DisplayName("findById 메소드")
    class Describe_findById {

        @Nested
        @DisplayName("해당되는 id의 product가 있다면")
        class Context_with_valid_id {

            private final Long VALID_ID = 1L;

            @BeforeEach
            void setUp() {
                given(productRepository.findById(VALID_ID)).willReturn(Optional.of(product1));
            }

            @Test
            @DisplayName("해당 Id의 product를 반환합니다.")
            void it_return_product() {
                assertThat(productService.findById(VALID_ID)).isEqualTo(product1);
            }
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private final Long INVALID_ID = 100L;

            @BeforeEach
            void setUp() {
                willThrow(new ProductNotFoundException(INVALID_ID)).given(productRepository).findById(INVALID_ID);
            }

            @Test
            @DisplayName("ProductNotFoundException을 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.findById(INVALID_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드")
    class Describe_update {

        private final Long VALID_ID = 1L;

        @BeforeEach
        void setUp() {
            willDoNothing().given(productRepository).update(any(Long.class), any(Product.class));
        }

        @Test
        @DisplayName("catToryRepository의 update를 호출합니다.")
        void it_call_update() {
            productService.update(VALID_ID, product1);
            verify(productRepository).update(any(Long.class), any(Product.class));
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private final Long INVALID_ID = 100L;

            @BeforeEach
            void setUp() {
                willThrow(new ProductNotFoundException(INVALID_ID)).given(productRepository).update(eq(INVALID_ID), any(Product.class));
            }

            @Test
            @DisplayName("ProductNotFoundException을 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productRepository.update(INVALID_ID, product2))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드")
    class Describe_deleteById {

        private final Long VALID_ID = 1L;

        @BeforeEach
        void setUp() {
            willDoNothing().given(productRepository).deleteById(any(Long.class));
        }

        @Test
        @DisplayName("catToryRepository의 update를 호출합니다.")
        void it_call_update() {
            productService.deleteById(VALID_ID);
            verify(productRepository).deleteById(any(Long.class));
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private final Long INVALID_ID = 100L;

            @BeforeEach
            void setUp() {
                willThrow(new ProductNotFoundException(INVALID_ID)).given(productRepository).deleteById(INVALID_ID);
            }

            @Test
            @DisplayName("ProductNotFoundException을 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.deleteById(INVALID_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}