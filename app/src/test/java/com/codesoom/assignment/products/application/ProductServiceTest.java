package com.codesoom.assignment.products.application;


import com.codesoom.assignment.exception.products.ProductNotFoundException;
import com.codesoom.assignment.products.domain.FakeProductRepository;
import com.codesoom.assignment.products.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.codesoom.assignment.support.ProductFieldFixture.TEST_NOT_EXIST;
import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@DisplayName("ProductService 유닛 테스트")
class ProductServiceTest {

    private final ProductService productService;
    private final FakeProductRepository fakeProductRepository;

    /*
    * Test의 DI는 Junit Engine의 ParameterResolver에 의하여 의존성이 주입되므로 @Autowired를 생략하지 않아야 합니다.
    */
    @Autowired
    ProductServiceTest(ProductService productService, FakeProductRepository fakeProductRepository) {
        this.productService = productService;
        this.fakeProductRepository = fakeProductRepository;
    }

    @BeforeEach
    void setUpDeleteFixture() {
        fakeProductRepository.deleteAllInBatch();
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
            @ValueSource(ints = {1, 3, 7})
            void it_returns_list(int createCount) {
                for (int i = 0; i < createCount; i++) {
                    productService.createProduct(TOY_1.요청_데이터_생성());
                }

                List<Product> products = productService.getProducts();

                assertThat(products)
                        .isNotEmpty()
                        .hasSize(createCount);
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
                Product productSource = productService.createProduct(TOY_1.요청_데이터_생성());

                Product product = productService.getProduct(productSource.getId());

                assertThat(product).isNotNull();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 장난감_생성_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class null이_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> productService.createProduct(null))
                        .isInstanceOf(NullPointerException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 새로운_상품이_주어지면 {
            @Test
            @DisplayName("상품을 저장하고 리턴한다")
            void it_returns_product() {
                Product product = productService.createProduct(TOY_1.요청_데이터_생성());

                assertThat(product).isNotNull();
                assertThat(product.getName()).isEqualTo(TOY_1.NAME());
                assertThat(product.getMaker()).isEqualTo(TOY_1.MAKER());
                assertThat(product.getPrice()).isEqualTo(TOY_1.PRICE());
                assertThat(product.getImgUrl()).isEqualTo(TOY_1.IMAGE());
            }
        }
    }
}
