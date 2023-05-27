package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
	}

	@Test
	public void getProductByValidId() throws ProductNotFoundException {
		Product product = productService.getProduct(VALID_ID);

		assertThat(product).isNotNull();
	}

	@Test
	public void getProductByInvalidId() {
		assertThatThrownBy(() -> productService.getProduct(INVALID_ID)).isInstanceOf(ProductNotFoundException.class);
	}

}
