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
    private final String SETUP_PRODUCT_IMAGE = "setupImage";

    private final String CREATED_PRODUCT_NAME = "createdName";
    private final String CREATED_PRODUCT_MAKER = "createdMaker";
    private final int CREATED_PRODUCT_PRICE = 200;
    private final String CREATED_PRODUCT_IMAGE = "createdImage";

    private final String UPDATED_PRODUCT_NAME = "updatedName";
    private final String UPDATED_PRODUCT_MAKER = "updatedMaker";
    private final int UPDATED_PRODUCT_PRICE = 300;
    private final String UPDATED_PRODUCT_IMAGE = "updatedImage";

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
        setupProduct = new Product();
        setupProduct.setId(EXISTED_ID);
        setupProduct.setName(SETUP_PRODUCT_NAME);
        setupProduct.setMaker(SETUP_PRODUCT_MAKER);
        setupProduct.setPrice(SETUP_PRODUCT_PRICE);
        setupProduct.setImage(SETUP_PRODUCT_IMAGE);

        products.add(setupProduct);
    }

    @Nested
    @DisplayName("getProducts 메서드는")
    class Describe_getProducts {
        @Test
        @DisplayName("고양이 장난감 목록을 리턴한다")
        void itReturnsListOfProducts() {
            given(productRepository.findAll()).willReturn(products);

            List<Product> list = productRepository.findAll();
            assertThat(list.size()).isEqualTo(products.size());

            verify(productRepository).findAll();
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

                Product detailProduct = productService.getProduct(givenExistedId);
                assertThat(detailProduct.getId()).isEqualTo(givenExistedId);
                assertThat(detailProduct.getName()).isEqualTo(SETUP_PRODUCT_NAME);
                assertThat(detailProduct.getMaker()).isEqualTo(SETUP_PRODUCT_MAKER);
                assertThat(detailProduct.getPrice()).isEqualTo(SETUP_PRODUCT_PRICE);
                assertThat(detailProduct.getImage()).isEqualTo(SETUP_PRODUCT_IMAGE);

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
        class Content_WithNameAndMakerAndPriceAndImage {
            private String givenName = CREATED_PRODUCT_NAME;
            private String givenMaker = CREATED_PRODUCT_MAKER;
            private int givenPrice = CREATED_PRODUCT_PRICE;
            private String givenImage = CREATED_PRODUCT_IMAGE;

            Product createProduct() {
                return new Product(CREATED_ID, givenName, givenMaker, givenPrice, givenImage);
            }

            @Test
            @DisplayName("새로운 장난감 고양이를 생성하고 생성된 장난감 고양이를 리턴한다")
            void itCreatesProductAndReturnsCreatedProduct() {
                Product createSource = createProduct();
                given(productRepository.save(any(Product.class))).willReturn(createSource);

                Product createdProduct = productService.createProduct(createSource);
                assertThat(createdProduct.getId())
                        .as("생성된 고양이 장난감의 아이디는 %f 이어야 한다", CREATED_ID)
                        .isEqualTo(CREATED_ID);
                assertThat(createdProduct.getName())
                        .as("생성된 고양이 장난감의 이름은 %s 이어야 한다", givenName)
                        .isEqualTo(givenName);
                assertThat(createdProduct.getMaker())
                        .as("생성된 고양이 장난감의 메이커는 %s 이어야 한다", givenMaker)
                        .isEqualTo(givenMaker);
                assertThat(createdProduct.getPrice())
                        .as("생성된 고양이 장난감의 가격은 %d 이어야 한다", givenPrice)
                        .isEqualTo(givenPrice);
                assertThat(createdProduct.getImage())
                        .as("생성하여 리턴 된 고양이 장난감의 이미지는 %s 이어야 한다", givenImage)
                        .isEqualTo(givenImage);

                verify(productRepository).save(any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_update {
        @Nested
        @DisplayName("만약 저징되어 있는 고양이 장난감의 아이디와 업데이트 될 이름, 메이커, 가격, 이미지가 주어진다면")
        class Context_WithExistedIdAndNameAndMakerAndPriceAndImage {
            private final Long givenExistedId = EXISTED_ID;
            private String givenName = UPDATED_PRODUCT_NAME;
            private String givenMaker = UPDATED_PRODUCT_MAKER;
            private int givenPrice = UPDATED_PRODUCT_PRICE;
            private String givenImage = UPDATED_PRODUCT_IMAGE;

            Product updateProduct() {
                return new Product(givenExistedId, givenName, givenMaker, givenPrice, givenImage);
            }

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감을 업데이트하고 해당 고양이 장난감을 리턴한다")
            void itUpdatesProductAndReturnsUpdatedProduct() {
                Product updateSource = updateProduct();
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product updatedProduct = productService.updateProduct(givenExistedId, updateSource);
                assertThat(updatedProduct.getId())
                        .as("업데이트된 고양이 장난감의 아이디는 %f 이어야 한다", EXISTED_ID)
                        .isEqualTo(EXISTED_ID);
                assertThat(updatedProduct.getName())
                        .as("업데이트된 고양이 장난감의 이름은 %s 이어야 한다", givenName)
                        .isEqualTo(givenName);
                assertThat(updatedProduct.getMaker())
                        .as("업데이트된 고양이 장난감의 메이커는 %s 이어야 한다", givenMaker)
                        .isEqualTo(givenMaker);
                assertThat(updatedProduct.getPrice())
                        .as("업데이트된 고양이 장난감의 가격은 %d 이어야 한다", givenPrice)
                        .isEqualTo(givenPrice);
                assertThat(updatedProduct.getImage())
                        .as("업데이트된 고양이 장난감의 이미지는 %s 이어야 한다", givenImage)
                        .isEqualTo(givenImage);

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
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감을 삭제하고 삭제 한 고양이 장난감을 리턴한다")
            void itDeletesProductAndReturnsDeletedProduct() {
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product deletedProduct = productService.deleteProduct(givenExistedId);
                assertThat(deletedProduct.getId())
                        .as("업데이트하여 리턴 된 고양이 장난감의 아이디는 %f 이어야 한다", EXISTED_ID)
                        .isEqualTo(EXISTED_ID);
                assertThat(deletedProduct.getName())
                        .as("업데이트하여 리턴 된 고양이 장난감의 이름은 %s 이어야 한다", SETUP_PRODUCT_NAME)
                        .isEqualTo(SETUP_PRODUCT_NAME);
                assertThat(deletedProduct.getMaker())
                        .as("업데이트하여 리턴 된 고양이 장난감의 메이커는 %s 이어야 한다", SETUP_PRODUCT_MAKER)
                        .isEqualTo(SETUP_PRODUCT_MAKER);
                assertThat(deletedProduct.getPrice())
                        .as("업데이트하여 리턴 된 고양이 장난감의 가격은 %d 이어야 한다", SETUP_PRODUCT_PRICE)
                        .isEqualTo(SETUP_PRODUCT_PRICE);
                assertThat(deletedProduct.getImage())
                        .as("업데이트하여 리턴 된 고양이 장난감의 이미지는 %s 이어야 한다", SETUP_PRODUCT_IMAGE)
                        .isEqualTo(SETUP_PRODUCT_IMAGE);

                verify(productRepository).delete(setupProduct);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 예외를 던진다")
            void itDeletesProductAndReturnsDeletedProduct() {
                given(productRepository.findById(givenNotExistedId))
                        .willThrow(ProductNotFoundException.class);

                assertThatThrownBy(() -> productService.deleteProduct(givenNotExistedId))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(givenNotExistedId);
            }
        }
    }
}