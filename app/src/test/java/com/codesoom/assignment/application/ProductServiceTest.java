package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            given(productRepository.findAll()).willReturn(new ArrayList<>());

            List<Product> products = productRepository.findAll();
            Assertions.assertEquals(products.size() , 1, "조회하여 리턴 된 고양이 장난감 목록은 size가 1이어야 한다");

            verify(productRepository).findAll();
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 id가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 id에 해당하는 고양이 장난감을 리턴한다")
            void itReturnsWithExistedProduct() {
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product detailProduct = productService.getProduct(givenExistedId);
                Assertions.assertEquals(detailProduct.getId(), EXISTED_ID, "조회하여 리턴 된 고양이 장난감은 id값이 1L이어야 한다");
                Assertions.assertEquals(detailProduct.getName(), SETUP_PRODUCT_NAME, "조회하여 리턴 된 고양이 장난감은 name값이 setupName이어야 한다");
                Assertions.assertEquals(detailProduct.getMaker(), SETUP_PRODUCT_MAKER, "조회하여 리턴 된 고양이 장난감은 maker값이 setupMaker이어야 한다");
                Assertions.assertEquals(detailProduct.getPrice(), SETUP_PRODUCT_PRICE, "조회하여 리턴 된 고양이 장난감은 price값이 100이어야 한다");
                Assertions.assertEquals(detailProduct.getImage(), SETUP_PRODUCT_IMAGE, "조회하여 리턴 된 고양이 장난감은 image값이 setupImage이어야 한다");

                verify(productRepository).findById(givenExistedId);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_class {
        @Nested
        @DisplayName("만약 name, maker, price, image가 주어진다면")
        class Content_WithNameAndMakerAndPriceAndImage {
            private String name;
            private String maker;
            private int price;
            private String image;

            @BeforeEach
            void prepareCreateProduct() {
                name = "createdName";
                maker = "createMaker";
                price = 200;
                image = "createdImage";
            }

            Product createProduct() {
                return new Product(CREATED_ID, name, maker, price, image);
            }

            @Test
            @DisplayName("새로운 장난감 고양이를 생성하고 생성된 장난감 고양이를 리턴한다")
            void itCreatesProductAndReturnsCreatedProduct() {
                Product createSource = createProduct();
                given(productRepository.save(any(Product.class))).willReturn(createSource);

                Product createdProduct = productService.createProduct(createSource);
                Assertions.assertEquals(createdProduct.getId(), CREATED_ID, "생성하여 리턴 된 고양이 장난감은 id값이 2L이어야 한다");
                Assertions.assertEquals(createdProduct.getName(), name, "생성하여 리턴 된 고양이 장난감은 name값이 createdName이어야 한다");
                Assertions.assertEquals(createdProduct.getMaker(), maker, "생성하여 리턴 된 고양이 장난감은 maker값이 createdMaker이어야 한다");
                Assertions.assertEquals(createdProduct.getPrice(), price, "생성하여 리턴 된 고양이 장난감은 price값이 200이어야 한다");
                Assertions.assertEquals(createdProduct.getImage(), image, "생성하여 리턴 된 고양이 장난감은 image값이 createdImage이어야 한다");

                verify(productRepository).save(any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_update {
        @Nested
        @DisplayName("만약 저징되어 있는 고양이 장난감의 id와 업데이트 될 name, maker, price, image가 주어진다면")
        class Context_WithExistedIdAndNameAndMakerAndPriceAndImage {
            private final Long givenExistedId = EXISTED_ID;
            private String name;
            private String maker;
            private int price;
            private String image;

            @BeforeEach
            void prepareUpdateProduct() {
                name = "updatedName";
                maker = "updatedMaker";
                price = 300;
                image = "updatedImage";
            }

            Product updateProduct() {
                return new Product(givenExistedId, name, maker, price, image);
            }

            @Test
            @DisplayName("주어진 id에 해당하는 고양이 장난감을 업데이트하고 수정된 고양이 장난감을 리턴한다")
            void itUpdatesProductAndReturnsUpdatedProduct() {
                Product updateSource = updateProduct();
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product updatedProduct = productService.updateProduct(givenExistedId, updateSource);
                Assertions.assertEquals(updatedProduct.getId(), givenExistedId, "업데이트하여 리턴 된 고양이 장난감은 id값이 1L이어야 한다");
                Assertions.assertEquals(updatedProduct.getName(), name, "업데이트하여 리턴 된 고양이 장난감은 name값이 updatedName이어야 한다");
                Assertions.assertEquals(updatedProduct.getMaker(), maker, "업데이트하여 리턴 된 고양이 장난감은 maker값이 updatedMaker이어야 한다");
                Assertions.assertEquals(updatedProduct.getPrice(), price, "업데이트하여 리턴 된 고양이 장난감은 price값이 300이어야 한다");
                Assertions.assertEquals(updatedProduct.getImage(), image, "업데이트하여 리턴 된 고양이 장난감은 image값이 udpatedImage이어야 한다");

                verify(productRepository).findById(givenExistedId);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_delete {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 id가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 id에 해당하는 고양이 장난감을 삭제한다")
            void itDeletesProduct() {
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                productService.deleteProduct(givenExistedId);

                verify(productRepository).delete(setupProduct);
            }
        }
    }
}