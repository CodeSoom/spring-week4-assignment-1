package com.codesoom.assignment;

import com.codesoom.assignment.controller.ProductController;
import com.codesoom.assignment.controller.ProductNotFoundException;
import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@Nested
@DisplayName("ProductController 클래스는")
public class ProductControllerTest {
    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);

        sutUpFixture();
    }

    private void sutUpFixture() {
        ProductDTO.Response productOne = ProductDTO.Response.of(
                new Product("test name 1", 1000, "test imageUrl 1", "test maker 1"));
        ProductDTO.Response productTwo = ProductDTO.Response.of(
                new Product("test name 2", 2000, "test imageUrl 2", "test maker 2"));

        given(productService.createProduct(any(ProductDTO.CreateProduct.class))).will(invocation -> {
            ProductDTO.CreateProduct createResponse = invocation.getArgument(0);
            return ProductDTO.Response.of(new Product(createResponse.getName(), createResponse.getPrice(),
                    createResponse.getImageUrl(), createResponse.getMaker()));
        });
        given(productService.updateProduct(any(int.class), any(ProductDTO.UpdateProduct.class))).will(invocation -> {
            ProductDTO.UpdateProduct updateResponse = invocation.getArgument(1);
            return ProductDTO.Response.of(new Product().updateProduct(updateResponse.getName(),
                    updateResponse.getPrice(), updateResponse.getImageUrl(), updateResponse.getMaker()));
        });
        given(productService.getProducts()).willReturn(Arrays.asList(productOne, productTwo));
        given(productService.getProduct(1)).willReturn(productOne);
        given(productService.getProduct(1000)).willThrow(ProductNotFoundException.class);
        given(productService.updateProduct(eq(1000), any(ProductDTO.UpdateProduct.class))).willThrow(ProductNotFoundException.class);
        willThrow(ProductNotFoundException.class).given(productService).deleteProduct(1000);

    }

    @Nested
    @DisplayName("GET /products URL 은")
    class GetProductsTest {

        @Test
        @DisplayName("http status code 200 과 ProductDTO.Response List 를 반환한다")
        void getProductsTest() {
            List<ProductDTO.Response> responses = productController.getProducts();

            verify(productService).getProducts();

            assertThat(responses.size()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("POST /products URL 은")
    class PostMethodTest {
        @Test
        @DisplayName("http status code 201 과 ProductDTO.Response 를 반환한다")
        void postMethodTest() {
            ProductDTO.CreateProduct createProduct = new ProductDTO.CreateProduct("create test name 1",
                    "create test maker 1", 3000, "create test imageUrl 1");
            ResponseEntity<ProductDTO.Response> response = productController.createProduct(createProduct);

            verify(productService).createProduct(createProduct);

            assertThat(response.getStatusCode().value()).isEqualTo(201);
            assertThat(response.getBody().getName()).isEqualTo("create test name 1");
        }
    }


    @Nested
    @DisplayName("DELETE /products/{id}")
    class DeleteProductTest {

        @Nested
        @DisplayName("withValidIdTest")
        class WithValidIdTest {
            @Test
            @DisplayName("http status code 204 를 반환한다")
            void deleteWithValidIdTest() {
                ResponseEntity<?> response = productController.deleteProduct(1);

                verify(productService).deleteProduct(1);

                assertThat(response.getStatusCode().value()).isEqualTo(204);
            }
        }

        @Nested
        @DisplayName("withInValidIdTest")
        class WithInValidIdTest {
            @Test
            @DisplayName("ProductNotFoundException 을 응답한다.")
            void deleteWithInValidIdTest() {
                assertThatThrownBy(() -> productController.deleteProduct(1000))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("GET /products/{id} URI 는")
    class GetProductTest {
        @Nested
        @DisplayName("유효한 id 가 주어지면 ")
        class WthValidIdTest {
            @Test
            @DisplayName("http status code 200 과 해당 ID의 ProductDTO.Response 를 응답한다.")
            void GetProductWithValidIdTest() {
                ResponseEntity<ProductDTO.Response> response = productController.getProduct(1);

                verify(productService).getProduct(1);

                assertThat(response.getStatusCode().value()).isEqualTo(200);
                assertThat(response.getBody().getName()).isEqualTo("test name 1");
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id 가 주어지면 ")
        class WithInvalidIdTest {
            @Test
            @DisplayName("ProductNotFoundException 을 응답한다.")
            void GetProductWithInValidIdTest() {
                assertThatThrownBy(() -> productController.getProduct(1000))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }


    @Nested
    @DisplayName("PUT /products/{id} URI 는")
    class PutProductTest {
        @Nested
        @DisplayName("유효한 id 가 주어지면")
        class WithValidIdTest {
            @Test
            @DisplayName("http status code 200 을 반환한다")
            void putProductWithValidIdTest() {
                ProductDTO.UpdateProduct updateProduct = new ProductDTO.UpdateProduct("update test name 1",
                        "update test maker 1", 1000, "update test imageUrl 1");
                ResponseEntity<ProductDTO.Response> response = productController.putProduct(1, updateProduct);

                verify(productService).updateProduct(1, updateProduct);

                assertThat(response.getStatusCode().value()).isEqualTo(200);
                assertThat(response.getBody().getName()).isEqualTo(updateProduct.getName());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id 가 주어지면")
        class WithInValidIdTest {
            @Test
            @DisplayName("ProductNotFound 를 응답한다.")
            void putProductWithInValidIdTest() {
                ProductDTO.UpdateProduct updateProduct = new ProductDTO.UpdateProduct("update test name 1",
                        "update test maker 1", 1000, "update test imageUrl 1");

                assertThatThrownBy(() -> productController.putProduct(1000, updateProduct))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("PATCH /products/{id}")
    class PatchMethodTest {

        @Nested
        @DisplayName("유효한 id 가 주어지면")
        class WithValidIdTest {
            @Test
            @DisplayName("http status code 200 을 반환한다")
            void patchProductWithValidIdTest() {
                ProductDTO.UpdateProduct updateProduct = new ProductDTO.UpdateProduct("update test name 1",
                        "update test maker 1", 1000, "update test imageUrl 1");
                ResponseEntity<ProductDTO.Response> response = productController.patchProduct(1, updateProduct);

                verify(productService).updateProduct(1, updateProduct);

                assertThat(response.getStatusCode().value()).isEqualTo(200);
                assertThat(response.getBody().getName()).isEqualTo(updateProduct.getName());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id 가 주어지면")
        class WithInValidIdTest {
            @Test
            @DisplayName("ProductNotFound 를 응답한다.")
            void patchProductWithInValidIdTest() {
                ProductDTO.UpdateProduct updateProduct = new ProductDTO.UpdateProduct("update test name 1",
                        "update test maker 1", 1000, "update test imageUrl 1");
                assertThatThrownBy(() -> productController.patchProduct(1000, updateProduct))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}

