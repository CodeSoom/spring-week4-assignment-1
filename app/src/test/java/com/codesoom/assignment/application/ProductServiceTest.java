package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("ProductService 테스트")
class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;

    private final String SETUP_PRODUCT_NAME = "setupName";
    private final String SETUP_PRODUCT_MAKER = "setupMaker";
    private final int SETUP_PRODUCT_PRICE = 100;
    private final String SETUP_PRODUCT_IMAGEURL = "setupImage";

    private final String CREATED_PRODUCT_NAME = "createdName";
    private final String CREATED_PRODUCT_MAKER = "createdMaker";
    private final int CREATED_PRODUCT_PRICE = 200;
    private final String CREATED_PRODUCT_IMAGEURL = "createdImage";

    private final String UPDATED_PRODUCT_NAME = "updatedName";
    private final String UPDATED_PRODUCT_MAKER = "updatedMaker";
    private final int UPDATED_PRODUCT_PRICE = 300;
    private final String UPDATED_PRODUCT_IMAGEURL = "updatedImage";

    private final Long EXISTED_ID = 1L;
    private final Long CREATED_ID = 2L;
    private final Long NOT_EXISTED_ID = 100L;

    private List<Product> products;
    private Product setupProduct;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        products = new ArrayList<>();
        setupProduct = Product.builder()
                .id(EXISTED_ID)
                .name(SETUP_PRODUCT_NAME)
                .maker(SETUP_PRODUCT_MAKER)
                .price(SETUP_PRODUCT_PRICE)
                .imageUrl(SETUP_PRODUCT_IMAGEURL)
                .build();

        products.add(setupProduct);
    }

    @Nested
    @DisplayName("getProducts 메서드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("만약 고양이 장난감 목록이 존재한다면")
        class Context_HasListOfProducts {
            private final Product mockProduct = Product.builder()
                    .id(CREATED_ID)
                    .name(CREATED_PRODUCT_NAME)
                    .maker(CREATED_PRODUCT_MAKER)
                    .price(CREATED_PRODUCT_PRICE)
                    .imageUrl(CREATED_PRODUCT_IMAGEURL)
                    .build();

            @BeforeEach
            void setUp() {
                productService.createProduct(mockProduct);
                products.add(mockProduct);
            }

            List<Product> listOfMockProducts() {
                return products;
            }

            @Test
            @DisplayName("저장되어 있는 고양이 장난감 목록을 리턴한다")
            void itReturnsListOfProducts() {
                List<Product> mockProducts = listOfMockProducts();
                given(productRepository.findAll()).willReturn(mockProducts);

                List<Product> Products = productService.getProducts();
                Product product = Products.get(0);

                assertThat(product.getName()).isEqualTo(setupProduct.getName());

                verify(productRepository).findAll();
            }
        }

        @Nested
        @DisplayName("만약 고양이 목록이 존재하지 않는다면")
        class Context_HasNotListOfProduct {
            @Test
            @DisplayName("비어있는 고양이 장난감 목록을 리턴한다")
            void itReturnsEmptyListOfProducts() {
                given(productRepository.findAll()).willReturn(new ArrayList<>());

                List<Product> products = productService.getProducts();

                assertThat(products).isEmpty();

                verify(productRepository).findAll();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감을 리턴한다")
            void itReturnsWithExistedProduct() {
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product product = productService.getProduct(givenExistedId);
                assertThat(product.getId()).isEqualTo(givenExistedId);

                verify(productRepository).findById(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 예외를 던진다")
            void itThrowsProductNotFoundException() {
                given(productRepository.findById(givenNotExistedId))
                        .willThrow(ProductNotFoundException.class);

                assertThatThrownBy(() -> productService.getProduct(givenNotExistedId))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(givenNotExistedId);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_class {
        @Nested
        @DisplayName("만약 이름, 메이커, 가격, 이미지가 주어진다면")
        class Content_WithNameAndMakerAndPriceAndImageUrl {
            private String givenName = CREATED_PRODUCT_NAME;
            private String givenMaker = CREATED_PRODUCT_MAKER;
            private int givenPrice = CREATED_PRODUCT_PRICE;
            private String givenImageUrl = CREATED_PRODUCT_IMAGEURL;

            Product createProduct() {
                return new Product(CREATED_ID, givenName, givenMaker, givenPrice, givenImageUrl);
            }

            @Test
            @DisplayName("장난감 고양이 객체를 저장하고 저장된 객체를 리턴한다")
            void itCreatesProductAndReturnsCreatedProduct() {
                Product createSource = createProduct();
                given(productRepository.save(any(Product.class))).willReturn(createSource);

                Product createdProduct = productService.createProduct(createSource);
                assertThat(createdProduct.getId())
                        .as("객체의 아이디는 %f 이어야 한다", CREATED_ID)
                        .isEqualTo(CREATED_ID);
                assertThat(createdProduct.getName())
                        .as("객체의 이름은 %s 이어야 한다", givenName)
                        .isEqualTo(givenName);
                assertThat(createdProduct.getMaker())
                        .as("객체의 메이커는 %s 이어야 한다", givenMaker)
                        .isEqualTo(givenMaker);
                assertThat(createdProduct.getPrice())
                        .as("객체의 가격은 %d 이어야 한다", givenPrice)
                        .isEqualTo(givenPrice);
                assertThat(createdProduct.getImageUrl())
                        .as("객체의 이미지는 %s 이어야 한다", givenImageUrl)
                        .isEqualTo(givenImageUrl);

                verify(productRepository).save(any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_update {
        @Nested
        @DisplayName("만약 저징되어 있는 고양이 장난감의 아이디와 업데이트 될 이름, 메이커, 가격, 이미지가 주어진다면")
        class Context_WithExistedIdAndNameAndMakerAndPriceAndImageUrl {
            private final Long givenExistedId = EXISTED_ID;
            private String givenName = UPDATED_PRODUCT_NAME;
            private String givenMaker = UPDATED_PRODUCT_MAKER;
            private int givenPrice = UPDATED_PRODUCT_PRICE;
            private String givenImageUrl = UPDATED_PRODUCT_IMAGEURL;

            Product updateProduct() {
                return new Product(givenExistedId, givenName, givenMaker, givenPrice, givenImageUrl);
            }

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감 객체를 업데이트하고 해당 객체를 리턴한다")
            void itUpdatesProductAndReturnsUpdatedProduct() {
                Product updateSource = updateProduct();
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product updatedProduct = productService.updateProduct(givenExistedId, updateSource);
                assertThat(updatedProduct.getId())
                        .as("객체의 아이디는 %f 이어야 한다", EXISTED_ID)
                        .isEqualTo(EXISTED_ID);
                assertThat(updatedProduct.getName())
                        .as("객체의 이름은 %s 이어야 한다", givenName)
                        .isEqualTo(givenName);
                assertThat(updatedProduct.getMaker())
                        .as("객체의 메이커는 %s 이어야 한다", givenMaker)
                        .isEqualTo(givenMaker);
                assertThat(updatedProduct.getPrice())
                        .as("객체의 가격은 %d 이어야 한다", givenPrice)
                        .isEqualTo(givenPrice);
                assertThat(updatedProduct.getImageUrl())
                        .as("객체의 이미지는 %s 이어야 한다", givenImageUrl)
                        .isEqualTo(givenImageUrl);

                verify(productRepository).findById(givenExistedId);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_delete {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감 객체를 삭제하고 해당 객체를 리턴한다")
            void itDeletesProductAndReturnsDeletedProduct() {
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product deletedProduct = productService.deleteProduct(givenExistedId);
                assertThat(deletedProduct.getId())
                        .as("객체의 아이디는 %f 이어야 한다", EXISTED_ID)
                        .isEqualTo(EXISTED_ID);
                assertThat(deletedProduct.getName())
                        .as("객체의 이름은 %s 이어야 한다", SETUP_PRODUCT_NAME)
                        .isEqualTo(SETUP_PRODUCT_NAME);
                assertThat(deletedProduct.getMaker())
                        .as("객체의 메이커는 %s 이어야 한다", SETUP_PRODUCT_MAKER)
                        .isEqualTo(SETUP_PRODUCT_MAKER);
                assertThat(deletedProduct.getPrice())
                        .as("객체의 가격은 %d 이어야 한다", SETUP_PRODUCT_PRICE)
                        .isEqualTo(SETUP_PRODUCT_PRICE);
                assertThat(deletedProduct.getImageUrl())
                        .as("객체의 이미지는 %s 이어야 한다", SETUP_PRODUCT_IMAGEURL)
                        .isEqualTo(SETUP_PRODUCT_IMAGEURL);

                verify(productRepository).delete(setupProduct);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 예외를 던진다")
            void itThrowsProductNotFoundException() {
                given(productRepository.findById(givenNotExistedId))
                        .willThrow(ProductNotFoundException.class);

                assertThatThrownBy(() -> productService.deleteProduct(givenNotExistedId))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(givenNotExistedId);
            }
        }
    }
}