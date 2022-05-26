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

	@BeforeEach
	void setUp() {
		productRepository = mock(ProductRepository.class);
		productService = new ProductService(productRepository);
	}

	void createProduct() {
		Product product = new Product("test name", 1000, "test imageUrl", "test maker");
		given(productRepository.findById(1)).willReturn(Optional.of(product));
		productService.createProduct(
			new ProductDTO.CreateProduct("test name", "test maker", 1000, "test imageUrl"));
	}

	@Nested
	@DisplayName("getProduct 메소드는")
	class getProductTest {
		@Test
		@DisplayName("해당 id 의 product 를 반환한다")
		public void getProduct() {
			createProduct();
			Product product = productService.getProduct(1);
			verify(productRepository).findById(1);
			assertThat(product.getName()).isEqualTo("test name");
		}
	}

	@Nested
	@DisplayName("createProduct 메소드는")
	class createProductTest {
		@Test
		@DisplayName("product 를 DB 에 저장한다")
		public void createProductTest() {
			createProduct();
			verify(productRepository).save(any(Product.class));
			assertThat(productRepository.findById(1).get().getName()).isEqualTo("test name");
		}
	}

	@Nested
	@DisplayName("delete 메소드는")
	class deleteProductTest {
		@Test
		@DisplayName("product 를 DB 에서 제거한다")
		public void deleteProduct() {
			createProduct();
			productService.deleteProduct(1);

			given(productRepository.findById(1)).willThrow(new IllegalArgumentException());
			verify(productRepository).deleteById(1);

			assertThatThrownBy(() -> productRepository.findById(1))
				.isInstanceOf(IllegalArgumentException.class);
		}
	}
}
