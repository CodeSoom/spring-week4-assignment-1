package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

// Repository를 주입하여 테스트하기
// 1.@DataJpaTest  //jpa를 직접 사용하여 테스트할 수 있다.
// 2. TaskRepository taskRepository = mock(TaskRepository.class)
@DisplayName("ProductService 클래스")
class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        List<Product> products = new ArrayList<>();

        Product product = Product.builder()
                .name("product Name")
                .maker("maker")
                .build();
        // 기존 week3에서는 service의 create가 올바르게 작동할 때만 정상 작동할 수 있었다.
        // 이제부터는 repository에만 의존하면 된다.
        products.add(product);

        //given
        given(productRepository.findAll()).willReturn(products);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(productRepository.findById(100L)).willReturn(Optional.empty());
        setUpSaveProduct();
    }

    void setUpSaveProduct() {
        //import static org.hamcrest.Matchers.any;  any()는 이것을 사용하였더니 localvariable오류. Hamcrest가 아닌 mockito사용.
        given(productRepository.save(ArgumentMatchers.any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(1L);
            return product;
        });
    }

    @Nested
    @DisplayName("getProducts 메소드")
    class describe_getProducts{

        @Test
        @DisplayName("List<Product>를 반환한다.")
        void it_returns_list(){

            List<Product> products = productService.getProducts();

            verify(productRepository).findAll();

            assertThat(products).isInstanceOf(List.class);

        }
    }

    @Nested
    class Describe_getProduct{

        @Nested
        @DisplayName("만약 유효한 id를 전달받았다면")
        class Context_with_a_valid_id {
            Product source = makeSource();

            @BeforeEach
            void setUp() {
                given(productRepository.findById(0L)).willReturn(Optional.of(source));
            }

            @Test
            @DisplayName("id에 해당하는 product를 반환한다.")
            void it_returns_a_product(){
                Long id = source.getId();
                Product result = productService.getProduct(id);

                verify(productRepository).findById(id);

                assertThat(result.getId()).isEqualTo(id);
                assertThat(result).isInstanceOf(Product.class);
            }
        }

        @Nested
        @DisplayName("만약 유효하지 못한 id를 전달받았다면")
        class Context_with_invalid_id{
            @DisplayName("파라미터가 잘못됬다는 예외를 던진다.")
            @Test
            void it_throws_an_exception(){
                assertThatThrownBy(() -> productService.getProduct(100L))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    class Describe_createProduct{
        //given
        Product source = makeSource();

        @Test
        @DisplayName("생성된 product를 리턴한다.")
        void it_returns_a_product() {
            //when
            Product result = productService.createProduct(source);

            //then
            verify(productRepository).save(source);
            assertThat(result).isInstanceOf(Product.class);
        }
    }

    //Update 테스트는 아직 개선되지 않았습니다.
    @Nested
    class Describe_update{

        @Nested
        @DisplayName("만약 유효한 id를 전달받았다면")
        class Context_with_valid_param{
            Long validId = 1L;
            Product source = makeSource();
            Product updateSource = makeSource();

            //Update 테스트는 아직 개선되지 않았습니다.
            @Test
            @DisplayName("수정한 product를 리턴한다.")
            void it_returns_a_product() {
                given(productRepository.save(source)).willReturn(source);
                //given(productRepository.findById(validId)).willReturn(Product.class);

                Product result = productService.updateProduct(validId, updateSource);
                assertThat(result).isInstanceOf(Product.class);
                assertThat(result.getName()).isEqualTo("TEST_VAR_NAME");
            }
        }

        @Nested
        @DisplayName("만약 유효한 id를 전달받지 못했을 경우")
        class Context_with_invalid_id{
            Long invalidId = 0L;
            Product source = makeSource();

            @Test
            @DisplayName("파라미터가 잘못됐다는 예외를 던진다.")
            void it_thorws_IllegalArgumentException() {
                Product source = makeSource();
                assertThatThrownBy(() -> productService.updateProduct(invalidId, source))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    class Describe_deleteProduct{

        @Nested
        @DisplayName("만약 유효한 id를 전달받았다면")
        class Context_with_valid_param{
            Long validId = 1L;

            @Test
            @DisplayName("삭제한 product를 리턴한다.")
            void it_returns_a_product() {
                Product result = productService.deleteProduct(validId);

                verify(productRepository).deleteById(validId);

                //todo Id 검증에 대한 테스트
                //given(productRepository.deleteById(0L)).willReturn(any(Product.class));
                //assertThat(result).isInstanceOf(Product.class);
                //assertThat(result.getId()).isEqualTo(validId);
            }
        }

        @Nested
        @DisplayName("만약 유효한 id를 전달받지 못했을 경우")
        class Context_with_invalid_id{
            Long invalidId = 0L;

            @Test
            @DisplayName("파라미터가 잘못됬다는 예외를 던진다.")
            void it_throws_IllegalArgumentException() {
                assertThatThrownBy(() -> productService.deleteProduct(invalidId))
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
