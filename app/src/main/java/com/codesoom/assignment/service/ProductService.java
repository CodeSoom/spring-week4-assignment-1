package com.codesoom.assignment.service;

import java.util.List;

import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ProductRepository;

public class ProductService {

	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public ProductDTO.Response getProduct(int id) {
		return ProductDTO.Response.of(
			productRepository.findById(id).orElseThrow(IllegalArgumentException::new));
	}

	public void createProduct(ProductDTO.CreateProduct createProduct) {
		Product product = new Product(createProduct);
		productRepository.save(product);
	}

	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}

	public List<ProductDTO> getProducts() {
	}
}
