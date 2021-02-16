package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    private static String PRODUCT_NAME = "testName";
    private static String CREATE_POSTFIX = "***";
    private static String UPDATE_POSTFIX = "@@@";
    private static Long INVALID_ID = 1000L;
    private static Long VALID_ID = 1L;

    @BeforeEach
    void setUp() {
        // Mockito를 사용하여 Mock Object 생성
        productRepository = mock(ProductRepository.class);

        // 생성자를 이용하여 Mock Object 주입
        productService = new ProductService(productRepository);

        setUpFixtures();
        setUpSaveProduct();
    }

    void setUpFixtures() {
        List<Product> products = new ArrayList<>();

        Product product = new Product(VALID_ID, PRODUCT_NAME, "testMaker", 1000, "testUrl");
        products.add(product);

        given(productRepository.findAll()).willReturn(products);

        given(productRepository.findById(VALID_ID)).willReturn(Optional.of(product));
        given(productRepository.findById(INVALID_ID)).willReturn(Optional.empty());
    }

    void setUpSaveProduct() {
        given(productRepository.save(any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(2L);
            return product;
        });
    }

    @Test
    @DisplayName("모든 상품을 조회한다.")
    void getProducts() {
        List<Product> products = productService.getProducts();

        // productService.findAll을 수행했는지 검증
        verify(productRepository).findAll();

        assertThat(products).hasSize(1);

        Product product = products.get(0);
        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(product.getId()).isEqualTo(VALID_ID);
    }

    @Test
    @DisplayName("상품목록에 있는 id를 조회하면, 그 id에 해당하는 상품을 리턴한다.")
    void getProductWithExistedId() {
        Product product = productService.getProduct(VALID_ID);

        verify(productRepository).findById(VALID_ID);

        assertThat(product.getName()).isEqualTo((PRODUCT_NAME));
    }

    @Test
    @DisplayName("상품목록에 없는 id를 조회하면, 예외를 던진다.")
    void getProductWithInvalidId() {
        assertThatThrownBy(() -> productService.getProduct(INVALID_ID))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(INVALID_ID);
    }

    @Test
    @DisplayName("상품을 생성한다.")
    void createProduct() {
        Product source = new Product();
        source.setName(PRODUCT_NAME + CREATE_POSTFIX);

        Product product = productService.createProduct(source);

        verify(productRepository).save(any(Product.class));

        assertThat(product.getId()).isEqualTo(2L);
        assertThat(product.getName()).isEqualTo(PRODUCT_NAME + CREATE_POSTFIX);
    }

    @Test
    @DisplayName("상품목록에 있는 id에 해당하는 상품을 수정하면, 수정된 상품을 리턴한다.")
    void updateProductWithExistedId() {
        Product source = new Product();
        source.setName(PRODUCT_NAME+UPDATE_POSTFIX);

        Product product = productService.updateProduct(1L, source);

        verify(productRepository).findById(1L);

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME+UPDATE_POSTFIX);
    }

    @Test
    @DisplayName("상품목록에 없는 id에 해당하는 상품을 수정하려고 하면, 예외를 던진다.")
    void updateProductWithNotExistedId() {
        Product source = new Product();
        source.setName(PRODUCT_NAME+UPDATE_POSTFIX);

        assertThatThrownBy(() -> productService.updateProduct(INVALID_ID, source))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(INVALID_ID);
    }

    @Test
    @DisplayName("상품목록에 있는 id에 해당하는 상품을 삭제한다.")
    void deleteProductWithExistedId() {
        productService.deleteProduct(VALID_ID);

        verify(productRepository).findById(VALID_ID);
        verify(productRepository).delete(any(Product.class));
    }

    @Test
    @DisplayName("상품목록에 없는 id에 해당하는 상품을 삭제하려고 하면, 예외를 던진다.")
    void deleteProductWithNotExistedId() {
        assertThatThrownBy(() -> productService.deleteProduct(INVALID_ID))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(INVALID_ID);
    }

}
