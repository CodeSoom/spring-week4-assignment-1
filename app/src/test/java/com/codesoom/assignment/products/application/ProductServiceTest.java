package com.codesoom.assignment.products.application;


import com.codesoom.assignment.exception.products.ProductNotFoundException;
import com.codesoom.assignment.products.domain.Product;
import com.codesoom.assignment.products.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codesoom.assignment.support.ProductFieldFixture.TEST_EXIST;
import static com.codesoom.assignment.support.ProductFieldFixture.TEST_NOT_EXIST;
import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@DisplayName("ProductService 유닛 테스트")
class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUpVariable() {
        productRepository = spy(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class getProducts_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_없을_때 {
            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_empty_list() {
                List<Product> products = productService.getProducts();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_있을_때 {
            @DisplayName("n개의 장난감 목록을 리턴한다")
            @ParameterizedTest(name = "{0}개의 장난감 목록을 리턴한다")
            @ValueSource(ints = {1, 77, 1101})
            void it_returns_list(int createCount) {
                createProductsUntilCount(createCount);

                List<Product> products = productService.getProducts();

                verify(productRepository).findAll();

                assertThat(products)
                        .isNotEmpty()
                        .hasSize(createCount);
            }

            private void createProductsUntilCount(int createCount) {
                List<Product> products = new ArrayList<>();

                for (int i = 0; i < createCount; i++) {
                    products.add(TOY_1.생성());
                }

                given(productRepository.findAll()).willReturn(products);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class getProduct_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> productService.getProduct(TEST_NOT_EXIST.ID()))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @Test
            @DisplayName("해당 id의 장난감 정보를 리턴한다")
            void it_returns_product() {
                given(productRepository.findById(TEST_EXIST.ID()))
                        .willReturn(Optional.ofNullable(TOY_1.생성(TEST_EXIST.ID())));

                Product product = productService.getProduct(TEST_EXIST.ID());

                assertThat(product).isNotNull();
                assertThat(product.getId()).isEqualTo(TEST_EXIST.ID());
            }
        }
    }
}
