package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

//Controller는 웹에서 들어온 요청을 서비스로 연결하는 중간고리 따라서 Controller에서 테스트해야할 것은 요청이 들어온 것에 대한 연결을 확인해 준다.
class ProductControllerTest {
    public static final long VALID_ID = 1L;
    public static final long INVALID_ID = 100;
    private ProductController controller;
    private ProductService productService;

    @BeforeEach
    void setUp() {

        //가능한 것
        // 1. Real object
        // 2. Mock object => 타입이 필요
        // 3. Spy -> Proxy Pattern => 진짜 오브젝트가 필요
        //productService = spy(new ProductService(productRepository));
        // 사용 : (Controller -> Spy -> Real Object)
        productService = mock(ProductService.class);
        controller = new ProductController(productService);

        List<Product> products = new ArrayList<>();
        Product initProduct = Product.builder()
                .name("init_name")
                .maker("init_maker")
                .price(2000)
                .imagePath("init_imagePath")
                .build();
        products.add(initProduct);

        // This Point
        // Service의 스팩을 어느정도 가늠할 수 있다. 따라서 이 부분과 Service의 스팩이 맞는 지 확인해야 한다.
        // 스팩이 맞지 않으면 테스트의 의미가 없다.
        // 각각의 유닛테스트는 다 통과하지만 함께 돌렸을 때는 작동하지 않는 경우가 있다. 이 경우 스팩이 어긋나는 경우가 가장 크다.
        // 통합테스트, Acceptence 테스트, E2E 테스 등을 충분하게 실행하여 외부와의 결합 위험에서 벗어나야 한다.
        given(productService.getProducts()).willReturn(products);
        given(productService.getProduct(VALID_ID)).willReturn(initProduct);
        given(productService.getProduct(INVALID_ID))
                .willThrow(new IllegalArgumentException());
        given(productService.deleteProduct(INVALID_ID))
                .willThrow(new IllegalArgumentException());
        given(productService.updateProduct(eq(INVALID_ID), any(Product.class)))
                .willThrow(new IllegalArgumentException());
    }

    @Nested
    class Describe_list{
        @Test
        void it_calls_productService_getProducts(){
            given(controller.list()).willReturn(new ArrayList<>());
            assertThat(controller.list()).isInstanceOf(List.class);
            verify(productService).getProducts();

            //assertThat(productController.list()).isInstanceOf(List.class); //if tested by spy
        }
    }

    @Nested
    class Describe_findById{
        @Test
        void it_calls_productService_getProduct(){
            assertThat(controller.findById(1L)).isInstanceOf(Product.class);
            verify(productService).getProduct(1L);
        }
    }


    @Nested
    class Describe_create{
        @Test
        void it_calls_productService_createProduct() {
            Product source = makeSource();
            controller.create(source);
            verify(productService).createProduct(source);
        }
    }

    @Nested
    class Describe_update{

        @Nested
        @DisplayName("유효한 id를 수정할 경우")
        class Context_with_valid_id{
            @Test
            void it_calls_productService_updateProduct() {
                Product source = makeSource();
                controller.update(VALID_ID, source);
                verify(productService).updateProduct(VALID_ID, source);
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id를 수정할 경우")
        class Context_with_invalid_id{
            @Test
            void it_throws_IllegalArgumentException() {
                Product source = makeSource();
                assertThatThrownBy(()-> controller.update(INVALID_ID, source))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

    }


    @Nested
    class Describe_deleteProduct{

        @Nested
        @DisplayName("유효한 id를 삭제할 경우")
        class Context_with_valid_id{
            @Test
            void it_calls_productService_deleteProduct(){
                controller.delete(VALID_ID);
                verify(productService).deleteProduct(VALID_ID);
            }
        }

        @Nested
        class Context_with_invalid_id{
            @Test
            void it_throws_IllegalArgumentException() {
                assertThatThrownBy(() -> controller.delete(INVALID_ID))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    private Product makeSource(){
        Product product = Product.builder()
                .name("TEST_VAR_NAME")
                .maker("TEST_VAR_MAKER")
                .price(3000)
                .imagePath("TEST_VAR_IMAGE_PATH")
                .build();
        return product;
    }

}
