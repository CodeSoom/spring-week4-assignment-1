package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.eception.ProductNotFoundException;
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

    public static final long VALID_ID = 1L;
    public static final long INVALID_ID = 100L;

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
        given(productRepository.findById(VALID_ID)).willReturn(Optional.of(product));
        given(productRepository.findById(INVALID_ID)).willReturn(Optional.empty());
        setUpSaveProduct();
    }

    void setUpSaveProduct() {
        //import static org.hamcrest.Matchers.any;  any()는 이것을 사용하였더니 localvariable오류. Hamcrest가 아닌 mockito사용.
        given(productRepository.save(ArgumentMatchers.any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(VALID_ID);
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
            assertThat(products.get(0)).isInstanceOf(Product.class);
        }
    }

    @Nested
    class Describe_getProduct{
        Long id;

        @Nested
        @DisplayName("만약 유효한 id를 전달받았다면")
        class Context_with_a_valid_id {
            Product source;

            @BeforeEach
            void setUp() {
                source = makeSource("");
                given(productRepository.findById(0L)).willReturn(Optional.of(source));
            }

            @Test
            @DisplayName("id에 해당하는 product를 반환한다.")
            void it_returns_a_product() {
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

            @BeforeEach
            void setUp() {
                id = INVALID_ID;
            }

            @DisplayName("파라미터가 잘못됬다는 예외를 던진다.")
            @Test
            void it_throws_an_exception(){
                assertThatThrownBy(
                        () -> productService.getProduct(id)
                ).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    class Describe_createProduct{
        Product source = makeSource("");

        @Test
        @DisplayName("생성된 product를 리턴한다.")
        void it_returns_a_product() {
            Product result = productService.createProduct(source);

            verify(productRepository).save(source);
            assertThat(result).isInstanceOf(Product.class);
        }
    }

    @Nested
    class Describe_update{
        Long id;
        Product source;
        Product updateSource;

        @BeforeEach
        void setUp() {
            source = makeSource("source");
            updateSource = makeSource("updateSource");
        }

        @Nested
        @DisplayName("만약 유효한 id를 전달받았다면")
        class Context_with_valid_param{

            @BeforeEach
            void setUp() {
                id = VALID_ID;
                given(productRepository.save(source)).willReturn(source);
            }

            @Test
            @DisplayName("수정한 product를 리턴한다.")
            void it_returns_a_product() {
                Product result = productService.updateProduct(id, updateSource);

                assertThat(result).isInstanceOf(Product.class);
                assertThat(result.getName()).isEqualTo("TEST_VAR_NAME" + "updateSource");
            }
        }

        @Nested
        @DisplayName("만약 유효한 id를 전달받지 못했을 경우")
        class Context_with_invalid_id{

            @BeforeEach
            void setUp() {
                id = INVALID_ID;
            }

            @Test
            @DisplayName("파라미터가 잘못됐다는 예외를 던진다.")
            void it_thorws_ProductNotFoundException() {
                assertThatThrownBy(() ->
                        productService.updateProduct(id, source)
                ).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    class Describe_deleteProduct{

        @Nested
        @DisplayName("만약 유효한 id를 전달받았다면")
        class Context_with_valid_param{
            Long id = VALID_ID;

            @Test
            @DisplayName("삭제한 product를 리턴한다.")
            void it_returns_a_product() {
                Product foundProduct = productService.getProduct(id);
                Product deletedProduct = productService.deleteProduct(id);

                verify(productRepository).deleteById(id);

                assertThat(foundProduct).isEqualTo(deletedProduct);
            }
        }

        @Nested
        @DisplayName("만약 유효한 id를 전달받지 못했을 경우")
        class Context_with_invalid_id{
            Long invalidId = 0L;

            @Test
            @DisplayName("파라미터가 잘못됬다는 예외를 던진다.")
            void it_throws_ProductNotFoundException() {
                assertThatThrownBy(() ->
                        productService.deleteProduct(invalidId)
                ).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    private Product makeSource(String postfix){
        Product product = Product.builder()
                .name("TEST_VAR_NAME" + postfix)
                .maker("TEST_VAR_MAKER" + postfix)
                .price(3000)
                .imagePath("TEST_VAR_IMAGE_PATH" + postfix)
                .build();
        return product;
    }
}
