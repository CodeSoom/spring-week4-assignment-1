package com.codesoom.assignment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ProductRepository;

@Service
@Transactional
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void createProduct(ProductDTO.CreateProduct createProduct) {
		Product product = new Product(createProduct);
		productRepository.save(product);
	}

	@Transactional(readOnly = true)
	public List<ProductDTO.Response> getProducts() {
		return productRepository.findAll()
			.stream()
			.map(product -> ProductDTO.Response.of(product))
			.collect(Collectors.toList());
	}

	public ProductDTO.Response updateProduct(int id, ProductDTO.UpdateProduct updateProduct) {
		Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);
		product.updateProduct(updateProduct);
		return ProductDTO.Response.of(product);
	}

	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public ProductDTO.Response getProduct(int id) {
		Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);
		return ProductDTO.Response.of(product);
	}
}
