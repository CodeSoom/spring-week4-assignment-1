package com.codesoom.assignment.product.adapter.in.web;


import com.codesoom.assignment.exception.products.ProductNotFoundException;
import com.codesoom.assignment.product.application.ProductService;
import com.codesoom.assignment.product.application.port.out.FakeCommandProductPort;
import com.codesoom.assignment.product.application.port.out.FakeQueryProductPort;
import com.codesoom.assignment.product.application.port.out.InMemoryProductsGenerator;
import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codesoom.assignment.support.ProductFieldFixture.TEST_NOT_EXIST;
import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static com.codesoom.assignment.support.ProductFixture.TOY_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductController 유닛 테스트")
class ProductControllerTest {
    private ProductController productController;

    @BeforeEach
    void setUpVariable() {
        InMemoryProductsGenerator productsGenerator = new InMemoryProductsGenerator();
        FakeQueryProductPort fakeQueryProductPort = new FakeQueryProductPort(productsGenerator.getProducts());
        FakeCommandProductPort fakeCommandProductPort = new FakeCommandProductPort(productsGenerator.getProducts());
        ProductService productService = new ProductService(fakeQueryProductPort, fakeCommandProductPort);
        productController = new ProductController(productService);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class list_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_없을_때 {
            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_empty_list() {
                List<Product> products = productController.list();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_있을_때 {
            @Test
            @DisplayName("비어있지 않은 리스트를 리턴한다")
            void it_returns_list() {
                productController.create(TOY_1.등록_요청_데이터_생성());

                assertThat(productController.list()).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class detail_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> productController.detail(TEST_NOT_EXIST.ID()))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @Test
            @DisplayName("해당 id의 장난감 정보를 리턴한다")
            void it_returns_product() {
                Product productSource = productController.create(TOY_1.등록_요청_데이터_생성());

                Product product = productController.detail(productSource.getId());

                assertThat(product).isNotNull();
                assertThat(product.getName()).isEqualTo(TOY_1.NAME());
                assertThat(product.getMaker()).isEqualTo(TOY_1.MAKER());
                assertThat(product.getPrice()).isEqualTo(TOY_1.PRICE());
                assertThat(product.getImgUrl()).isEqualTo(TOY_1.IMAGE());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class create_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class null이_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> productController.create(null))
                        .isInstanceOf(NullPointerException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 새로운_상품이_주어지면 {
            @Test
            @DisplayName("상품을 저장하고 리턴한다")
            void it_returns_product() {
                Product product = productController.create(TOY_1.등록_요청_데이터_생성());

                assertThat(product).isNotNull();
                assertThat(product.getName()).isEqualTo(TOY_1.NAME());
                assertThat(product.getMaker()).isEqualTo(TOY_1.MAKER());
                assertThat(product.getPrice()).isEqualTo(TOY_1.PRICE());
                assertThat(product.getImgUrl()).isEqualTo(TOY_1.IMAGE());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class update_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> productController.update(TEST_NOT_EXIST.ID(), TOY_2.수정_요청_데이터_생성()))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            private Long fixtureId;

            @BeforeEach
            void setUpCreateFixture() {
                Product productSource = productController.create(TOY_1.등록_요청_데이터_생성());
                fixtureId = productSource.getId();
            }

            @Nested
            @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
            class 변경_할_상품_정보가_null인_경우 {
                @Test
                @DisplayName("예외를 던진다")
                void it_returns_exception() {
                    assertThatThrownBy(() -> productController.update(fixtureId, null))
                            .isInstanceOf(NullPointerException.class);
                }
            }

            @Nested
            @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
            class 변경_할_상품_정보가_null이_아닌_경우 {
                @Test
                @DisplayName("상품을 수정하고 리턴한다")
                void it_returns_product() {
                    Product product = productController.update(fixtureId, TOY_2.수정_요청_데이터_생성());

                    assertThat(product).isNotNull();
                    assertThat(product.getId()).isEqualTo(fixtureId);
                    assertThat(product.getName()).isEqualTo(TOY_2.NAME());
                    assertThat(product.getMaker()).isEqualTo(TOY_2.MAKER());
                    assertThat(product.getPrice()).isEqualTo(TOY_2.PRICE());
                    assertThat(product.getImgUrl()).isEqualTo(TOY_2.IMAGE());
                }
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class delete_메서드는 {
        private Long fixtureId;

        @BeforeEach
        void setUpCreateFixture() {
            Product productSource = productController.create(TOY_1.등록_요청_데이터_생성());
            fixtureId = productSource.getId();
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> productController.delete(TEST_NOT_EXIST.ID()))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @Test
            @DisplayName("상품을 삭제한다")
            void it_returns_product() {
                Product product = productController.detail(fixtureId);

                assertThat(product).isNotNull();

                productController.delete(fixtureId);

                assertThatThrownBy(() -> productController.detail(fixtureId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
