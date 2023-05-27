package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAll() {
		return productRepository.findAll();
	}

	public Product getProduct(long productId) throws ProductNotFoundException {
		return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
	}

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(long productId, Product product) {
		Product savedProduct = getProduct(productId);

		return savedProduct.updateDetail(product);
	}

	public void deleteProduct(long productId) {
		Product savedProduct = getProduct(productId);

		productRepository.delete(savedProduct);
	}
}
