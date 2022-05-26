package com.codesoom.assignment.service;

import com.codesoom.assignment.repository.ProductRepository;

public class ProductService {

	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
}
