package com.codesoom.assignment.service;

import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ProductRepository;

public class ProductService {

	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product getProduct(int i) {
		return productRepository.findById(i).orElseThrow(IllegalArgumentException::new);
	}
}
