package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.errors.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
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
import static org.mockito.Mockito.verify;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    private static final String PRODUCT_NAME = "test";
    private static final String SAVE_POSTFIX = "save";
    private static final String UPDATE_POSTFIX = "update";
    private static final Long productId = 1L;
    private static final Long wrongId = 100L;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        Product product = new Product(productId, PRODUCT_NAME, null, 0, null);

        List<Product> products = new ArrayList<>();
        products.add(product);

        given(productRepository.findAll()).willReturn(products);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(productRepository.findById(eq(wrongId))).willThrow(ProductNotFoundException.class);
    }

    @DisplayName("getProducts는 저장하고 있는 상품 목록을 리턴한다")
    @Test
    void getProducts() {
        List<Product> products = productService.getProducts();

        verify(productRepository).findAll();

        assertThat(products).hasSize(1);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class getProduct_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_있다면 {
            @Test
            void 상품을_리턴한다() {
                Product product = productService.getProduct(productId);

                verify(productRepository).findById(productId);

                assertThat(product.getId()).isEqualTo(productId);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_없다면 {
            @Test
            void 예외를_던진다() {
                assertThatThrownBy(() -> productService.getProduct(wrongId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @DisplayName("saveProduct는 주어진 상품을 저장한다")
    @Test
    void saveProduct() {
        given(productRepository.save(any(Product.class)))
                .will(invocation -> invocation.getArgument(0));

        String productName = PRODUCT_NAME + SAVE_POSTFIX;
        Product source = Product.createSaveProduct(productName, null, 0, null);

        Product product = productService.saveProduct(source);

        verify(productRepository).save(source);

        assertThat(product.getName()).isEqualTo(productName);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class updateProduct_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_있다면 {
            @Test
            void 상품을_수정한다() {
                String productName = PRODUCT_NAME + UPDATE_POSTFIX;
                Product source = new Product(null, productName, null, 0, null);

                Product product = productService.updateProduct(productId, source);

                assertThat(product.getName()).isEqualTo(productName);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_없다면 {
            @Test
            void 예외를_던진다() {
                String productName = PRODUCT_NAME + UPDATE_POSTFIX;
                Product source = new Product(null, productName, null, 0, null);

                assertThatThrownBy(() -> productService.updateProduct(wrongId, source))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class deleteProduct_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_있다면 {
            @Test
            void 상품을_삭제한다() {
                productService.deleteProduct(productId);

                verify(productRepository).delete(any(Product.class));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_없다면 {
            @Test
            void 예외를_던진다() {
                assertThatThrownBy(() -> productService.deleteProduct(wrongId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
