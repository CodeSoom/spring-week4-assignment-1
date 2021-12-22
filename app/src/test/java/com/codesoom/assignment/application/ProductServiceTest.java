package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private static final String PRODUCT_NAME = "test";
    private static final String CREATE_POSTFIX = "...";
    private static final String UPDATE_POSTFIX = "!!!";

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        // subject
        productService = new ProductService(productRepository);
    }

    private List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setMaker(PRODUCT_NAME);

        products.add(product);
        return products;
    }

    private Product getProduct() {
        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setMaker(PRODUCT_NAME);

        products.add(product);
        return product;
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts{
        @Nested
        @DisplayName("Product가 등록되어 있다면")
        class Context_have_product{

            @BeforeEach
            void prepareProduct(){
                List<Product> products = getProducts();
                given(productRepository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("등록된 모든 Product를 리턴한다")
            void It_return_products(){
                List<Product> products = productService.getProducts();

                verify(productRepository).findAll();

                assertThat(products).hasSize(1);

                Product task = products.get(0);
                assertThat(task.getMaker()).isEqualTo(PRODUCT_NAME);
            }
        }
        @Nested
        @DisplayName("Product가 등록되어 있지 않다면")
        class Context_have_not_product{

            @BeforeEach
            void prepareProduct(){
                List<Product> products = productService.getProducts();
                products.forEach(product -> productService.deleteProduct(product.getId()));
            }

            @Test
            @DisplayName("비어 있는 리스트를 리턴한다")
            void it_return_products(){
                assertThat(productService.getProducts()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct{
        @Nested
        @DisplayName("등록된 Product의 id가 주어지면")
        class Context_withValid_id{

            @BeforeEach
            void prepareProduct(){
                Product product = getProduct();
                given(productRepository.findById(1L)).willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("해당 Product를 리턴한다")
            void It_return_product(){
                Product found = productService.getProduct(1L);

                verify(productRepository).findById(1L);

                assertThat(found.getMaker()).isEqualTo(PRODUCT_NAME);
            }
        }

        @Nested
        @DisplayName("Product를 찾을 수 없는 id가 주어지면")
        class Context_withInvalid_id{

            @BeforeEach
            void prepareProduct(){
                given(productRepository.findById(100L)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("Product를 찾을 수 없다는 내용의 예외를 던진다.")
            void It_return_error(){
                assertThatThrownBy(()-> productService.getProduct(100L)).isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(100L);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct{
        @Nested
        @DisplayName("등록할 product가 주어진다면")
        class Context_with_product{

            @BeforeEach
            void prepareProduct() {
                Product createdProduct = new Product();
                createdProduct.setMaker(PRODUCT_NAME + CREATE_POSTFIX);

                given(productRepository.save(any(Product.class))).will(invocation -> {
                    Product product = invocation.getArgument(0);
                    product.setId(2L);
                    return product;
                });
            }

            @Test
            @DisplayName("Product를 생성하고 리턴한다.")
            void It_return_product(){
                Product source = new Product();
                source.setMaker(PRODUCT_NAME + CREATE_POSTFIX);

                Product product = productService.createProduct(source);

                verify(productRepository).save(any(Product.class));

                assertThat(product.getId()).isEqualTo(2L);
                assertThat(product.getMaker()).isEqualTo(PRODUCT_NAME + CREATE_POSTFIX);
            }
        }

        @Nested
        @DisplayName("NULL값이 주이지면")
        class Context_without_product{

            @Test
            @DisplayName("내용이 없다는 NullPointerException를 리턴한다.")
            void it_return_error(){
                Product product = null;

                assertThatThrownBy(()-> productService.createProduct(product)).isInstanceOf(NullPointerException.class);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct{
        @Nested
        @DisplayName("등록된 Product의 id가 주어진다면")
        class Context_withValid_id{

            @BeforeEach
            void prepareProduct(){
                Product product = getProduct();
                given(productRepository.findById(1L)).willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("등록되어있는 Product를 수정하고 리턴한다.")
            void it_return_product(){
                Product source = new Product();
                source.setMaker(PRODUCT_NAME + UPDATE_POSTFIX);

                Product product = productService.updateProduct(1L, source);

                verify(productRepository).findById(1L);

                assertThat(product.getMaker()).isEqualTo(PRODUCT_NAME + UPDATE_POSTFIX);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 Product의 id가 주어진다면")
        class Context_withInvalid_id{

            @BeforeEach
            void prepareProduct(){
                Product product = getProduct();
                given(productRepository.findById(1L)).willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("Product를 찾을수 없다는 예외를 던진다.")
            void It_return_error(){
                Product source = new Product();
                source.setMaker(PRODUCT_NAME + UPDATE_POSTFIX);

                assertThatThrownBy(()-> productService.updateProduct(100L, source))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(100L);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_deleteProduct{
        @Nested
        @DisplayName("등록된 Product의 id가 주어진다면")
        class Context_withValid_id{

            @BeforeEach
            void prepareProduct(){
                Product product = getProduct();
                given(productRepository.findById(1L)).willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("등록되어있는 Product를 삭제하고 리턴한다.")
            void It_return_product(){
                productService.deleteProduct(1L);

                verify(productRepository).findById(1L);
                verify(productRepository).delete(any(Product.class));
            }
        }

        @Nested
        @DisplayName("등록되지 않은 Product의 id가 주어진다면")
        class Context_withInvalid_id{

            @BeforeEach
            void prepareProduct(){
                Product product = getProduct();
                given(productRepository.findById(1L)).willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("Product를 찾을 수 없다는 예외를 던진다.")
            void It_return_error(){
                assertThatThrownBy(()-> productService.deleteProduct(100L))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(100L);
            }
        }
    }
}
