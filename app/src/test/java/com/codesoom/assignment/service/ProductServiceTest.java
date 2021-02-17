package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private final long ID = 1L;

    private final long NOT_EXIST_ID = 100L;

    private ProductService productService;

    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        product = new Product(ID, "장난감 뱀", "장난감 컴퍼니", 10000, "뱀.jpg");
    }

    @Nested
    @DisplayName("getProducts()는 ")
    class Describe_getProducts {
        @Nested
        @DisplayName("저장된 장난감이 있으면")
        class Context_exist_product {
            @BeforeEach
            void setUp() {
                List<Product> products = new ArrayList<>();
                products.add(product);

                given(productRepository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("장난감 리스트를 반환한다")
            void it_return_product_list() {
                List<Product> products = productService.getProducts();

                assertThat(products).hasSize(1);

                verify(productRepository).findAll();
            }
        }

        @Nested
        @DisplayName("저장된 장난감이 없으면")
        class Context_does_not_exist_product {
            @BeforeEach
            void setUp() {
                given(productRepository.findAll()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void it_return_empty_list() {
                List<Product> products = productService.getProducts();

                assertThat(products).hasSize(0);

                verify(productRepository).findAll();
            }
        }
    }

    @Nested
    @DisplayName("getProduct()는 ")
    class Describe_getProduct {
        @Nested
        @DisplayName("존재하는 id로 장난감을 조회하면")
        class Context_exist_id {

            @BeforeEach
            void setUp() {
                given(productRepository.findById(ID)).willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("id에 해당하는 장난감을 반환한다")
            void it_return_product() {
                Product findProduct = productService.getProduct(ID);

                assertThat(findProduct).isEqualTo(product);

                verify(productRepository).findById(ID);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id로 장난감을 조회하면")
        class Context_does_not_exist_id {
            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_return_product_not_found_exception() {
                assertThrows(ProductNotFoundException.class, () -> productService.getProduct(NOT_EXIST_ID));

                verify(productRepository).findById(NOT_EXIST_ID);
            }
        }
    }

    @Nested
    @DisplayName("addProduct()는 ")
    class Describe_addProduct {
        @Nested
        @DisplayName("ProductDto를 Product로 변경하고 ")
        class Context_change_productdto_to_product {
            Product newProduct = new Product(2L, "장난감 뱀2", "장난감 컴퍼니", 20000, "뱀.jpg");

            @BeforeEach
            void setUp() {
                given(productRepository.save(newProduct)).willReturn(newProduct);
            }

            @Test
            @DisplayName("Product를 저장한다.")
            void it_return_product() {
                Product findProduct = productService.addProduct(new ProductDto(newProduct));

                assertThat(findProduct.getName()).isEqualTo("장난감 뱀2");

                verify(productRepository).save(newProduct);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct()는 ")
    class Describe_updateProduct {
        @Nested
        @DisplayName("id에 해당하는 장난감이 존재하면")
        class Context_exist_id {
            Product source;
            ProductDto productDto;

            @BeforeEach
            void setUp() {
                source = new Product(ID, "수정된 뱀", "장난감 컴퍼니", 20000, "변경된.jpg");
                productDto = new ProductDto(source);
                given(productRepository.findById(ID)).willReturn(Optional.of(product));
                given(productRepository.save(source)).willReturn(source);
            }

            @Test
            @DisplayName("변경된 장난감을 반환한다")
            void it_return_updated_product() {
                Product updatedProduct = productService.updateProduct(ID, productDto);

                verify(productRepository).findById(ID);

                assertThat(updatedProduct.getName()).isEqualTo("수정된 뱀");
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id로 장난감을 조회하면")
        class Context_does_not_exist_id {
            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_return_product_not_found_exception() {
                assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(NOT_EXIST_ID, new ProductDto()));

                verify(productRepository).findById(NOT_EXIST_ID);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct()는 ")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("장난감이 존재하는 id이면")
        class Context_exist_id {

            @BeforeEach
            void setUp() {
                given(productRepository.findById(ID)).willReturn(Optional.of(product));
                given(productRepository.existsById(ID)).willReturn(true);

            }

            @Test
            @DisplayName("id에 해당하는 장난감을 삭제한다.")
            void it_delete_product() {
                productService.deleteProduct(ID);

                verify(productRepository).existsById(ID);
                verify(productRepository).deleteById(ID);
            }
        }

        @Nested
        @DisplayName("장난감이 존재하지 않는 id이면")
        class Context_does_not_exist_id {
            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_product_not_found_exception() {
                assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(NOT_EXIST_ID));
            }
        }
    }
}
