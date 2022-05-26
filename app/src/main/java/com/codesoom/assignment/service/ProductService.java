package com.codesoom.assignment.service;

import java.util.List;
import java.util.stream.Collectors;

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

	public ProductDTO.Response createProduct(ProductDTO.CreateProduct createProduct) {
		return ProductDTO.Response.of(productRepository.save(new Product(createProduct)));
	}

	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}

	public List<ProductDTO.Response> getProducts() {
		return productRepository.findAll()
			.stream()
			.map(product -> ProductDTO.Response.of(product))
			.collect(Collectors.toList());
	}

	public ProductDTO.UpdateProduct updateProduct(int id, ProductDTO.UpdateProduct source) {
		Product product = productRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);
		return new ProductDTO.UpdateProduct(product.updateProduct(source));
	}
}
