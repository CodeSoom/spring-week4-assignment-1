package com.codesoom.assignment;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ProductRepository;
import com.codesoom.assignment.service.ProductService;

@Nested
@DisplayName("ProductService 클래스는")
public class ProductServiceTest {
	private ProductRepository productRepository;
	private ProductService productService;
	private static Product TEST_PRODUCT_1 = new Product("test name 1", 1000, "test imageUrl 1", "test maker 1");
	private static Product TEST_PRODUCT_2 = new Product("test name 2", 2000, "test imageUrl 2", "test maker 2");

	@BeforeEach
	void setUp() {
		productRepository = mock(ProductRepository.class);
		productService = new ProductService(productRepository);
	}

	void createProduct(Product product) {
		given(productRepository.findById(1)).willReturn(Optional.of(product));
		productService.createProduct(
			new ProductDTO.CreateProduct(product.getName(), product.getMaker(), product.getPrice(),
				product.getImageUrl()));
	}

	@Nested
	@DisplayName("getProduct 메소드는")
	class getProductTest {
		@Test
		@DisplayName("해당 id 의 product 를 반환한다")
		public void getProductTest() {
			createProduct(TEST_PRODUCT_1);
			ProductDTO.Response response = productService.getProduct(1);
			verify(productRepository).findById(1);
			assertThat(response.getName()).isEqualTo("test name 1");
		}
	}

	@Nested
	@DisplayName("createProduct 메소드는")
	class createProductTest {
		@Test
		@DisplayName("product 를 DB 에 저장한다")
		public void createProductTest() {
			createProduct(TEST_PRODUCT_1);
			verify(productRepository).save(any(Product.class));
			assertThat(productRepository.findById(1).get().getName()).isEqualTo("test name 1");
		}
	}

	@Nested
	@DisplayName("deleteProduct 메소드는")
	class deleteProductTest {
		@Test
		@DisplayName("product 를 DB 에서 제거한다")
		public void deleteProductTest() {
			createProduct(TEST_PRODUCT_1);
			productService.deleteProduct(1);

			given(productRepository.findById(1)).willThrow(new IllegalArgumentException());
			verify(productRepository).deleteById(1);

			assertThatThrownBy(() -> productRepository.findById(1))
				.isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Nested
	@DisplayName("getProducts 메소드는")
	class getProductsTest {
		@Test
		@DisplayName("product 를 전부 반환한다")
		public void getProductsTest() {
			createProduct(TEST_PRODUCT_1);
			productService.getProducts();

			given(productRepository.findById(1)).willThrow(new IllegalArgumentException());
			verify(productRepository).deleteById(1);

			assertThatThrownBy(() -> productRepository.findById(1))
				.isInstanceOf(IllegalArgumentException.class);
		}
	}
}
