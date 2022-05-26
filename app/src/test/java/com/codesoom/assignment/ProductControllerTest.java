package com.codesoom.assignment;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.codesoom.assignment.controller.ProductController;
import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.service.ProductService;

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

		given(productService.getProducts()).willReturn(Arrays.asList(productOne, productTwo));
	}

	@Nested
	@DisplayName("/products URL 은")
	class getProductsTest {

		@Nested
		@DisplayName("get method 는")
		class getMethod{
			@Test
			@DisplayName("ProductDTO.Response list 를 반환한다")
			void getProductsTest() {
				ResponseEntity<List<ProductDTO.Response>> responses = productController.getProducts();

				verify(productService).getProducts();

				assertThat(responses.getStatusCode().value()).isEqualTo(200);
				assertThat(responses.getBody().size()).isEqualTo(2);
			}
		}

		@Nested
		@DisplayName("post method 는")
		class postMethod{
			@Test
			@DisplayName("ProductDTO.Response list 를 반환한다")
			void postProductsTest() {
				ResponseEntity<List<ProductDTO.Response>> responses = productController.getProducts();

				verify(productService).getProducts();

				assertThat(responses.getStatusCode().value()).isEqualTo(200);
				assertThat(responses.getBody().size()).isEqualTo(2);
			}
		}

		@Nested
		@DisplayName("delete method 는")
		class deleteMethod{
			@Test
			@DisplayName("ProductDTO.Response list 를 반환한다")
			void deleteProductsTest() {
				ResponseEntity<?> responses = productController.deleteProduct();

				verify(productService).deleteProduct(1);

				assertThat(responses.getStatusCode().value()).isEqualTo(204);
			}
		}
	}
}

