package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("CatController 클래스는")
public class ProductControllerTest {
    private static final String TEST_NAME = "testName";
    private static final String TEST_MAKER = "testMaker";
    private static final Long TEST_PRICE = 5000L;
    private static final String TEST_IMAGE_PATH = "testImagePath.jpg";
    private static final String CREATE_POSTFIX = "...";
    private static final String UPDATE_POSTFIX = "!!!";

    private ProductController productController;
    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName(TEST_NAME);
        product.setMaker(TEST_MAKER);
        product.setPrice(TEST_PRICE);
        product.setImagePath(TEST_IMAGE_PATH);
        products.add(product);

        given(productService.getProducts()).willReturn(products);

        given(productService.getProduct(1L)).willReturn(product);

//        given(productService.getProduct(100L))
//                .willThrow(new ProductNotFoundException(100L));
//
//        given(productService.updateProduct(eq(100L), any(Product.class)))
//                .willThrow(new ProductNotFoundException(100L));
//
//        given(productService.deleteProduct(100L))
//                .willThrow(new ProductNotFoundException(100L));

        productController = new ProductController(productService);
    }

    @Test
    @DisplayName("list 메소드는 Products에 있는 모든 Product를 반환합니다.")
    void list() {
        assertThat(productController.list()).isNotEmpty();
        assertThat(productController.list()).hasSize(1);
    }

    @Nested
    @DisplayName("detail 메소드는")
    class Describe_detail {
        @Nested
        @DisplayName("클라이언트가 요청한 CatToy의 id가 CatToys에 있으면")
        class Context_with_valid_id {
            @Test
            @DisplayName("id에 해당하는 CatToy를 반환합니다.")
            void detailWithValidId() {
                assertThat(productController.detail(1L)
                        .getName()).isEqualTo(TEST_NAME);
            }
        }

        @Nested
        @DisplayName("클라이언트가 요청한 CatToy의 id가 CatToys에 없으면")
        class Context_with_invalid_id {
            @Test
            @DisplayName("CatToyNotFoundException 예외를 던집니다.")
            void detailWithInvalidId() {
                assertThatThrownBy(() -> productController.detail(100L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Test
    @DisplayName("create 메소드는 클라이언트가 요청한 새로운 CatToy를 CatToys에 추가해줍니다.")
    void create() {
        int oldSize = productController.list().size();

        Product source = new Product();
        source.setName(TEST_NAME + CREATE_POSTFIX);
        source.setMaker(TEST_MAKER + CREATE_POSTFIX);
        source.setPrice(TEST_PRICE + 1000L);
        source.setImagePath(CREATE_POSTFIX + TEST_IMAGE_PATH);

        productController.create(source);

        int newSize = productController.list().size();

        assertThat(newSize - oldSize).isEqualTo(1);
        assertThat(productController.list()).hasSize(2);
        assertThat(productController.list().get(0).getId()).isEqualTo(1L);
        assertThat(productController.list().get(0).getName()).isEqualTo(TEST_NAME);
        assertThat(productController.list().get(1).getId()).isEqualTo(2L);
        assertThat(productController.list().get(1).getName()).isEqualTo(TEST_NAME + CREATE_POSTFIX);
    }

    @Test
    @DisplayName("update 메소드는 CatToys에서 클라이언트가 요청한 id에 해당하는 CatToy의 " +
            "name, maker, price, imagePath를 변경해줍니다.")
    void update() {
        Long id = Long.valueOf(productController.list().size());
        Product product = productController.detail(id);

        assertThat(product.getName()).isEqualTo(TEST_NAME);

        product.setName(TEST_NAME + UPDATE_POSTFIX);

        productController.update(id, product);
        product = productController.detail(id);

        assertThat(product.getName()).isEqualTo(TEST_NAME + UPDATE_POSTFIX);
    }

    @Test
    @DisplayName("patch 메소드는 CatToys에서 클라이언트가 요청한 id에 해당하는 CatToy의 " +
            "name, maker, price, imagePath를 변경해줍니다.")
    void patch() {
        Long id = Long.valueOf(productController.list().size());
        Product product = productController.detail(id);

        assertThat(product.getMaker()).isEqualTo(TEST_MAKER);

        product.setMaker(TEST_MAKER + UPDATE_POSTFIX);

        productController.update(id, product);
        product = productController.detail(id);

        assertThat(product.getMaker()).isEqualTo(TEST_MAKER + UPDATE_POSTFIX);
    }

    @Test
    @DisplayName("delete 메소드는 CatToys에서 클라이언트가 요청한 id에 해당하는 CatToy를 지웁니다.")
    void delete() {
        int oldSize = productController.list().size();

        productController.delete(Long.valueOf(oldSize));

        int newSize = productController.list().size();

        assertThat(oldSize - newSize).isEqualTo(1);
        assertThat(productController.list()).hasSize(0);
    }
}
