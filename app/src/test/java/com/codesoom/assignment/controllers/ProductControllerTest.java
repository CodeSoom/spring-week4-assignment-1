package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.infra.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductControllerTest {

    private static final String PRODUCT_MAKER = "test1";
    private static final String PRODUCT_MAKER2= "test2";
    private static final String UPDATE_POSTFIX = "!!!";
    private static final Long INVALID_ID = 0L;
    private static final Long VALID_ID = 1L;

    private ProductController controller;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryRepository();

        productService = new ProductService(productRepository);
        controller = new ProductController(productService);

        Product product = new Product();
        product.setMaker(PRODUCT_MAKER);

        controller.create(product);
    }

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list{
        @Nested
        @DisplayName("등록된 Product가 있다면")
        class Contest_have_product{

            @Test
            @DisplayName("등록된 모든 Product를 리턴한다")
            void It_return_list(){
                List<Product> products = controller.list();
                assertThat(products).hasSize(1);
                assertThat(products.get(0).getMaker()).isEqualTo(PRODUCT_MAKER);

            }
        }

        @Nested
        @DisplayName("Product가 등록되어 있지 않다면")
        class Context_have_not_product{

            @Test
            @DisplayName("비어 있는 리스트를 리턴한다.")
            void it_return_products(){
                List<Product> products = controller.list();
                controller.delete(VALID_ID);

                assertThat(products).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("create메소드는")
    class Describe_create{
        @Nested
        @DisplayName(" product가 주어진다면")
        class Context_with_product{

            @Test
            @DisplayName("Product를 생성하고 리턴한다.")
            void it_return_product(){
                int oldSize = controller.list().size();

                Product product = new Product();
                product.setMaker(PRODUCT_MAKER2);

                controller.create(product);
                int newSize = controller.list().size();

                assertThat(newSize - oldSize).isEqualTo(1);
                assertThat(controller.list().get(1).getId()).isEqualTo(2L);
                assertThat(controller.list().get(1).getMaker()).isEqualTo(PRODUCT_MAKER2);
            }
        }
    }

    @Nested
    @DisplayName("detail메소드는")
    class Describe_detail{
        @Nested
        @DisplayName("등록된 Product의 id가 주어지면")
        class Context_withValid_id{

            @Test
            @DisplayName("해당 Product를 리턴한다")
            void it_return_product(){
                Product product = controller.detail(VALID_ID);
                assertThat(product.getId()).isEqualTo(VALID_ID);
                assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
            }
        }

        @Nested
        @DisplayName("Product를 찾을 수 없는 id가 주어지면")
        class Context_withInvalid_id{

            @Test
            @DisplayName("Product를 찾을 수 없다는 내용의 예외를 던진다.")
            void it_return_error(){
                assertThatThrownBy(() -> controller.delete(INVALID_ID)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("update메서드는")
    class Describe_update{
        @Nested
        @DisplayName("등록된 Product의 id가 주어진다면")
        class Context_withValid_id{

            @Test
            @DisplayName("등록되어있는 Product를 수정하고 리턴한다")
            void it_return_product(){
                Product source = new Product();
                source.setMaker(UPDATE_POSTFIX + PRODUCT_MAKER);
                controller.update(1L, source);

                Product product = controller.detail(1L);

                assertThat(product.getId()).isEqualTo(VALID_ID);
                assertThat(product.getMaker()).isEqualTo(UPDATE_POSTFIX + PRODUCT_MAKER);
                assertThat(controller.list().get(0).getId()).isEqualTo(VALID_ID);
                assertThat(controller.list().get(0).getMaker()).isEqualTo(UPDATE_POSTFIX + PRODUCT_MAKER);
            }
        }
    }

    @Nested
    @DisplayName("patch메서드는")
    class Describe_patch{
        @Nested
        @DisplayName("등록된 Product의 id가 주어진다면")
        class Context_withValid_id{

            @Test
            @DisplayName("등록되어있는 Product를 수정하고 리턴한다")
            void it_return_product(){
                Product source = new Product();
                source.setMaker(UPDATE_POSTFIX + PRODUCT_MAKER);
                controller.update(1L, source);

                Product product = controller.detail(1L);

                assertThat(product.getId()).isEqualTo(VALID_ID);
                assertThat(product.getMaker()).isEqualTo(UPDATE_POSTFIX + PRODUCT_MAKER);
                assertThat(controller.list().get(0).getId()).isEqualTo(VALID_ID);
                assertThat(controller.list().get(0).getMaker()).isEqualTo(UPDATE_POSTFIX + PRODUCT_MAKER);
            }
        }
    }

    @Nested
    @DisplayName("삭제 요청 메소드는")
    class Describe_delete{
        @Nested
        @DisplayName("등록되어있는 id가 주어진다면")
        class Context_withValid_id{

            @Test
            @DisplayName("등록되어있는 Product를 삭제하고 리턴한다")
            void It_return_product(){
                controller.delete(1L);

                assertThat(controller.list().size()).isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 id가 주어진다면")
        class Context_withInvalid_id{

            @Test
            @DisplayName("Product를 찾을 수 없다는 예외를 던진다.")
            void it_return_error(){
                assertThatThrownBy(() -> controller.delete(INVALID_ID)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
