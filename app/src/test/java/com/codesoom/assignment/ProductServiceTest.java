package com.codesoom.assignment;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
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

	@BeforeEach
	void setUp() {
		productRepository = mock(ProductRepository.class);
		productService = new ProductService(productRepository);
		setUpFixtures();
	}

	void setUpFixtures() {
		Product productOne = new Product("test name 1", 1000, "test imageUrl 1", "test maker 1");
		Product productTwo = new Product("test name 2", 2000, "test imageUrl 2", "test maker 2");
		List<Product> testProductList = Arrays.asList(productOne, productTwo);

		given(productRepository.findById(1)).willReturn(Optional.of(productOne));
		given(productRepository.findById(2)).willReturn(Optional.of(productTwo));

		given(productRepository.findAll()).willReturn(testProductList);

		given(productRepository.save(any(Product.class))).will(invocation -> {
			Product product = invocation.getArgument(0);
			return product;
		});
	}

	@Nested
	@DisplayName("getProduct 메소드는")
	class getProductTest {
		@Test
		@DisplayName("해당 id 의 product 를 반환한다")
		public void getProductTest() {
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
			ProductDTO.Response response = productService.createProduct(
				new ProductDTO.CreateProduct("create test name", "create test maker", 3000, "create test imageUrl"));
			verify(productRepository).save(any(Product.class));
			assertThat(response.getName()).isEqualTo("create test name");
		}
	}

	@Nested
	@DisplayName("deleteProduct 메소드는")
	class deleteProductTest {
		@Test
		@DisplayName("product 를 DB 에서 제거한다")
		public void deleteProductTest() {
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
			List<ProductDTO.Response> responseList = productService.getProducts();
			assertThat(responseList.size()).isEqualTo(2);
		}
	}

	@Nested
	@DisplayName("updateProducts 메소드는")
	class updateProductsTest {
		@Test
		@DisplayName("update 된 Product 를 반환한다")
		public void updateProductsTest() {
			ProductDTO.UpdateProduct source = new ProductDTO.UpdateProduct("update test name",
				"update test maker", 3000, "update test imageUrl");
			ProductDTO.Response updateProduct = productService.updateProduct(1, source);
			verify(productRepository).findById(1);
			assertThat(updateProduct.getName()).isEqualTo("update test name");
		}
	}
}
