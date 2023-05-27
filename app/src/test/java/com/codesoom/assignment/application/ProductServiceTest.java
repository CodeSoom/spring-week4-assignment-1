package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ProductServiceTest {

	private ProductService productService;
	private ProductRepository productRepository;

	private final long VALID_ID = 1L;
	private final long INVALID_ID = 100L;


	@BeforeEach
	public void setUp() {
		productRepository = mock(ProductRepository.class);
		productService = new ProductService(productRepository);

		fixture();
	}

	private void fixture() {
		Product product = new Product();
		product.setName("쥐방울");
		product.setMaker("냥냥상회");
		product.setPrice(5000);
		product.setImageUrl("");

		when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
		when(productRepository.findById(VALID_ID)).thenReturn(Optional.of(product));
		when(productRepository.findById(INVALID_ID)).thenThrow(new ProductNotFoundException(INVALID_ID));
		when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
			Product savedProduct = invocation.getArgument(0);
			savedProduct.setId(2L);
			return savedProduct;
		});
	}

	@Test
	public void getAllProducts() {
		List<Product> products = productRepository.findAll();

		assertThat(products).hasSize(1);

		verify(productRepository).findAll();
	}
	@Test
	public void getProductByValidId() throws ProductNotFoundException {
		Product product = productService.getProduct(VALID_ID);

		assertThat(product).isNotNull();

		verify(productRepository).findById(VALID_ID);
	}

	@Test
	public void getProductByInvalidId() {
		assertThatThrownBy(() -> productService.getProduct(INVALID_ID)).isInstanceOf(ProductNotFoundException.class);

		verify(productRepository).findById(INVALID_ID);
	}

	@Test
	public void createProduct() {
		Product product = new Product();
		product.setName("쥐방울2");
		product.setMaker("냥냥상회");
		product.setPrice(5000);
		product.setImageUrl("");

		Product savedProduct = productService.createProduct(product);

		verify(productRepository).save(product);

		assertThat(savedProduct.getId()).isEqualTo(2L);
		assertThat(savedProduct.equals(product)).isTrue();
	}

	@Test
	public void updateProduct() {
		Product product = new Product();
		product.setName("쥐방울_update");
		product.setMaker("냥냥상회2");
		product.setPrice(9000);
		product.setImageUrl("");

		Product modifiedProduct = productService.updateProduct(1L, product);

		verify(productRepository).findById(1L);

		assertThat(modifiedProduct.equals(product)).isTrue();
	}

	@Test
	public void deleteProduct() {
		productService.deleteProduct(1L);

		verify(productRepository).findById(1L);
		verify(productRepository).delete(any(Product.class));
	}

}
