package com.codesoom.assignment.application;

import com.codesoom.assignment.common.exception.InvalidParamException;
import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.controller.ProductDto;
import com.codesoom.assignment.controller.ProductDtoMapper;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductCommand;
import com.codesoom.assignment.domain.ProductInfo;
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

@DisplayName("ProductService 클래스")
class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    private final String EXCEPTION_MESSAGE = "요청하신 상품이 없습니다.";

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("데이터가 존재한다면")
        class Context_with_non_empty_data {
            private final List<Product> givenProducts = new ArrayList<>();

            @BeforeEach
            void prepare() {
                Product product1 = Product.builder()
                        .id(1L)
                        .name("고양이 장난감1")
                        .maker("삼성")
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();
                Product product2 = Product.builder()
                        .id(2L)
                        .name("고양이 장난감2")
                        .maker("애플")
                        .price(12000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                givenProducts.add(product1);
                givenProducts.add(product2);

                given(productRepository.findAll()).willReturn(givenProducts);
            }

            @Test
            @DisplayName("모든 상품을 리턴한다")
            void it_returns_all_products() {
                List<ProductInfo> actualProducts = productService.getProducts();

                verify(productRepository).findAll();

                assertThat(actualProducts).hasSize(givenProducts.size());
            }
        }

        @Nested
        @DisplayName("데이터가 존재하지 않는다면")
        class Context_with_empty_data {
            @BeforeEach
            void prepare() {
                given(productRepository.findAll()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("빈 데이터를 리턴한다")
            void it_returns_empty_data() {
                List<ProductInfo> actualProducts = productService.getProducts();

                assertThat(actualProducts).hasSize(0);
            }
        }

    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private final Long PRODUCT_ID = 1L;
            private Product givenProduct;

            @BeforeEach
            void prepare() {
                givenProduct = Product.builder()
                        .id(PRODUCT_ID)
                        .name("고양이 장난감1")
                        .maker("삼성")
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.of(givenProduct));
            }

            @Test
            @DisplayName("상품을 찾아 리턴한다")
            void it_returns_searched_product() {
                ProductInfo actualProduct = productService.getProduct(PRODUCT_ID);

                verify(productRepository).findById(PRODUCT_ID);

                assertThat(actualProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(actualProduct.getMaker()).isEqualTo(givenProduct.getMaker());
                assertThat(actualProduct.getPrice()).isEqualTo(givenProduct.getPrice());
            }
        }

        @Nested
        @DisplayName("유효하지않은 데이터가 주어지면")
        class Context_with_invalid_id {
            @BeforeEach
            void prepare() {
                given(productRepository.findById(any(Long.class))).willThrow(new ProductNotFoundException(EXCEPTION_MESSAGE));
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.getProduct(100L)).isInstanceOf(ProductNotFoundException.class);
            }
        }

    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {
        @Nested
        @DisplayName("새로운 상품이 주어지면")
        class Context_with_new_product {
            private Product givenProduct;

            @BeforeEach
            void prepare() {
                givenProduct = Product.builder()
                        .id(1L)
                        .name("고양이 장난감1")
                        .maker("삼성")
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                given(productRepository.save(any(Product.class))).willReturn(givenProduct);
            }

            @Test
            @DisplayName("DB에 등록하고 등록된 상품을 리턴한다")
            void it_returns_registered_product() {
                ProductDto.RequestParam request = new ProductDto.RequestParam();
                request.setName("고양이 장난감1");
                request.setMaker("삼성");
                request.setPrice(10000L);
                request.setImageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png");

                ProductDtoMapper mapper = new ProductDtoMapper();
                ProductCommand.Register command = mapper.of(request);

                ProductInfo actualProduct = productService.createProduct(command);

                verify(productRepository).save(any(Product.class));

                assertThat(actualProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(actualProduct.getMaker()).isEqualTo(givenProduct.getMaker());
                assertThat(actualProduct.getPrice()).isEqualTo(givenProduct.getPrice());
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private Product givenProduct;

            @BeforeEach
            void prepare() {
                givenProduct = Product.builder()
                        .id(1L)
                        .name("고양이 장난감1")
                        .maker("삼성")
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                given(productRepository.findById(any(Long.class))).willReturn(Optional.of(givenProduct));
            }

            @Test
            @DisplayName("상품을 수정하고 수정된 상품을 리턴한다")
            void it_returns_modified_product() {
                ProductDto.RequestParam request = new ProductDto.RequestParam();
                request.setId(1L);
                request.setName("고양이 장난감1");
                request.setMaker("삼성");
                request.setPrice(12000L);
                request.setImageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png");

                ProductDtoMapper mapper = new ProductDtoMapper();
                ProductCommand.Register command = mapper.of(request);

                ProductInfo actualProduct = productService.updateProduct(command);

                assertThat(actualProduct.getPrice()).isEqualTo(12000L);
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id {
            @BeforeEach
            void prepare() {
                given(productRepository.findById(any(Long.class))).willThrow(new ProductNotFoundException(EXCEPTION_MESSAGE));
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                ProductDto.RequestParam request = new ProductDto.RequestParam();
                request.setId(100L);
                request.setName("고양이 장난감100");
                request.setMaker("삼성");
                request.setPrice(10000L);
                request.setImageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png");

                ProductDtoMapper mapper = new ProductDtoMapper();
                ProductCommand.Register command = mapper.of(request);

                assertThatThrownBy(() -> productService.updateProduct(command)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private Product givenProduct;

            @BeforeEach
            void prepare() {
                givenProduct = Product.builder()
                        .id(1L)
                        .name("고양이 장난감1")
                        .maker("삼성")
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                given(productRepository.findById(any(Long.class))).willReturn(Optional.of(givenProduct));
            }
            @Test
            @DisplayName("해당 상품을 삭제한다")
            void it_returns_nothing() {
                productService.deleteProduct(1L);

                verify(productRepository).delete(givenProduct);
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id {
            @BeforeEach
            void prepare() {
                given(productRepository.findById(any(Long.class))).willThrow(new ProductNotFoundException(EXCEPTION_MESSAGE));
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.deleteProduct(100L)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
