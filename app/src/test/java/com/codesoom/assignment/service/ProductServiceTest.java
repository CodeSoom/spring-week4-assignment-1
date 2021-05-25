package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.error.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.web.WebAppConfiguration;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@WebAppConfiguration
@DataJpaTest
@DisplayName("상품CRUD")
class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private static final Long INVALID_ID = 1000L;
    private static final Long VALID_ID = 1L;
    private static final String PRODUCT_NAME = "test";
    private static final String UPDATE_POSTFIX = "_update";

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

        Product product = Product.builder().id(VALID_ID).name(PRODUCT_NAME).maker("testMaker").price(1000).imgUrl("testUrl").build();

        products.add(product);

        given(productRepository.findAll()).willReturn(products);

        given(productRepository.findById(VALID_ID)).willReturn(Optional.of(product));
        given(productRepository.findById(INVALID_ID)).willReturn(Optional.empty());
    }

    // 학습하기 위해 어떤 키워드를 알아야할까?
    void setUpSaveProduct() {
        given(productRepository.save(any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(0);
            return product;
        });
    }

    @Test
    @DisplayName("모든 상품을 조회한다.")
    void getProducts() {
        List<Product> products = productService.findAll();

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
        Product product = productService.findById(VALID_ID);

        verify(productRepository).findById(VALID_ID);

        assertThat(product.getName()).isEqualTo((PRODUCT_NAME));
    }

    @Test
    @DisplayName("상품목록에 없는 id를 조회하면, 예외를 던진다.")
    void getProductWithInvalidId() {
        assertThatThrownBy(() -> productService.findById(INVALID_ID))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(INVALID_ID);
    }

    @Test
    @DisplayName("상품을 생성한다.")
    void createProduct() {
        Product source = new Product();
        source.setName(PRODUCT_NAME);

        Product product = productService.save(source);

        verify(productRepository).save(any(Product.class));

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
    }

    @Test
    @DisplayName("상품목록에 있는 id에 해당하는 상품을 수정하면, 수정된 상품을 리턴한다.")
    void updateProductWithExistedId() {
        Product source = new Product();
        source.setName(PRODUCT_NAME+UPDATE_POSTFIX);

        Product product = productService.update(1L, source);

        verify(productRepository).findById(1L);

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME+UPDATE_POSTFIX);
    }

    @Test
    @DisplayName("상품목록에 없는 id에 해당하는 상품을 수정하려고 하면, 예외를 던진다.")
    void updateProductWithNotExistedId() {
        Product source = new Product();
        source.setName(PRODUCT_NAME+UPDATE_POSTFIX);

        assertThatThrownBy(() -> productService.update(INVALID_ID, source))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(INVALID_ID);
    }

    @Test
    @DisplayName("상품목록에 있는 id에 해당하는 상품을 삭제한다.")
    void deleteProductWithExistedId() {
        productService.delete(VALID_ID);

        verify(productRepository).findById(VALID_ID);
        verify(productRepository).delete(any(Product.class));
    }

    @Test
    @DisplayName("상품목록에 없는 id에 해당하는 상품을 삭제하려고 하면, 예외를 던진다.")
    void deleteProductWithNotExistedId() {
        assertThatThrownBy(() -> productService.delete(INVALID_ID))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(INVALID_ID);
    }
}