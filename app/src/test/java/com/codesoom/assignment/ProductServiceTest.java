package com.codesoom.assignment;

import com.codesoom.assignment.exceptions.ToyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ToyService 클래스")
public class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    private Product randomToy() {
        int randomNumber = (int) (Math.random() * 1000);
        return new Product(
                "Toy" + randomNumber,
                "Maker" + randomNumber,
                randomNumber,
                "URL" + randomNumber
        );
    }

    @Nested
    @DisplayName("getToys 메소드는")
    class Describe_getToys {

        @Nested
        @DisplayName("아무런 toy가 등록되지 않았다면")
        class Context_no_toys {

            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_empty_list() {
                final List<Product> products = productService.getToys();

                assertThat(products).hasSize(0);
            }
        }

        @Nested
        @DisplayName("1개의 toy만 등록되어 있다면")
        class Context_one_toy {
            private Product product;

            @BeforeEach
            void setUp() {
                product = randomToy();
                productService.register(product);
            }

            @Test
            @DisplayName("1개의 Toy만이 들어있는 리스트를 반환한다.")
            void it_returns_list_of_one_toy() {
                final List<Product> products = productService.getToys();

                assertThat(products).hasSize(1);
                assertThat(products.get(0)).isEqualTo(product);
            }
        }

        @Nested
        @DisplayName("n개 이상의 toy가 등록되어 있다면")
        class Context_multiple_toys {
            private List<Product> expectedProducts = new ArrayList<>();

            void setUp(int testCase) {
                for (int i = 0; i < testCase; i++) {
                    Product randomProduct = randomToy();
                    expectedProducts.add(randomProduct);
                    productService.register(randomProduct);
                }
            }

            @DisplayName("n개의 Toy만이 들어있는 리스트를 반환한다.")
            @ParameterizedTest
            @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20})
            void it_returns_list_of_n_toys(int testCase) {
                setUp(testCase);

                final List<Product> products = productService.getToys();

                assertThat(products).hasSize(testCase);
                for (int i = 0; i < testCase; i++) {
                    assertThat(products.get(i)).isEqualTo(this.expectedProducts.get(i));
                }
            }
        }
    }

    @Nested
    @DisplayName("getToyById 메소드는")
    class Describe_getProductById {

        @Nested
        @DisplayName("인자로 넘긴 ID의 Toy를 발견하면")
        class Context_found {
            private Product product;

            @BeforeEach
            void setUp() {
                product = productService.register(randomToy());
            }

            @Test
            @DisplayName("해당 Toy를 리턴한다")
            void it_returns_according_toy() throws ToyNotFoundException {
                Product foundProduct = productService.getToyById(product.getId());

                assertThat(foundProduct).isEqualTo(product);
            }
        }

        @Nested
        @DisplayName("인자로 넘긴 ID의 Toy를 발견하지 못하면")
        class Context_not_found {
            @Test
            @DisplayName("404 Not Found Exception을 던진다.")
            void it_throws_404_not_found_exception() {
                long ID_NOT_EXISTS = Long.MAX_VALUE;
                assertThatThrownBy(() -> productService.getToyById(ID_NOT_EXISTS))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("register 메소드는")
    class Describe_register {

        @Nested
        @DisplayName("정상적인 상황에서")
        class Context_normal {
            private Product product;

            @BeforeEach
            void setUp() {
                product = randomToy();
            }

            @Test
            @DisplayName("인자로 넘겨진 Toy를 등록하고, 등록된 Toy를 리턴한다")
            void it_returns_registered_toy() {
                Product registeredProduct = productService.register(product);

                assertThat(registeredProduct).isEqualTo(product);
            }
        }

        @Nested
        @DisplayName("여러 태스크를 추가해도")
        class Context_multiple_tasks {
            void registerMultipleToys(int testCase) {
                for (int i = 0; i < testCase; i++) {
                    Product randomProduct = randomToy();
                    productService.register(randomProduct);
                }
            }

            @DisplayName("모두 다른 ID를 부여한다")
            @ParameterizedTest
            @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20})
            void it_returns_all_different_ids(int testCase) {
                registerMultipleToys(testCase);
                final List<Product> products = productService.getToys();

                Set<Long> ids = products.stream().map(Product::getId).collect(Collectors.toSet());
                assertThat(ids).hasSize(testCase);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("인자로 넘겨진 ID에 해당하는 Toy를 찾으면")
        class Context_toy_found {
            private Product targetProduct;

            @BeforeEach
            void setUp() {
                targetProduct = productService.register(randomToy());
            }

            @Test
            @DisplayName("해당 Toy를 인자로 넘겨진 Toy의 정보로 업데이트하고, 업데이트된 Toy를 리턴한다")
            void it_returns_updated_toy() throws ToyNotFoundException {
                Product newProduct = randomToy();
                Product updated = productService.update(targetProduct.getId(), newProduct);

                assertThat(updated.getId()).isEqualTo(targetProduct.getId());
                assertThat(updated).isEqualTo(newProduct);
            }
        }

        @Nested
        @DisplayName("인자로 넘겨진 ID에 해당하는 Toy를 찾지 못하면")
        class Context_toy_not_found {
            @Test
            @DisplayName("404 Not Found Exception을 던진다.")
            void it_throws_404_not_found_exception() {
                long ID_NOT_EXISTS = Long.MAX_VALUE;
                assertThatThrownBy(() -> productService.update(ID_NOT_EXISTS, randomToy()))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

            @Nested
            @DisplayName("인자로 넘겨진 ID에 해당하는 Toy를 찾으면")
            class Context_toy_found {
                private Product targetProduct;

                @BeforeEach
                void setUp() {
                    targetProduct = productService.register(randomToy());
                }

                @Test
                @DisplayName("해당 Toy를 삭제한다")
                void it_returns_deleted_toy() throws ToyNotFoundException {
                    productService.delete(targetProduct.getId());

                    assertThatThrownBy(() -> productService.getToyById(targetProduct.getId()))
                            .isInstanceOf(ToyNotFoundException.class);
                }
            }

            @Nested
            @DisplayName("인자로 넘겨진 ID에 해당하는 Toy를 찾지 못하면")
            class Context_toy_not_found {
                @Test
                @DisplayName("404 Not Found Exception을 던진다.")
                void it_throws_404_not_found_exception() {
                    long ID_NOT_EXISTS = Long.MAX_VALUE;
                    assertThatThrownBy(() -> productService.delete(ID_NOT_EXISTS))
                            .isInstanceOf(ToyNotFoundException.class);
                }
            }
    }
}
