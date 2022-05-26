package com.codesoom.assignment.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO.Response>> getProducts() {
		return ResponseEntity.ok(productService.getProducts());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO.Response> getProduct(@PathVariable("id") int id) {
		return ResponseEntity.ok(productService.getProduct(id));
	}

	@PostMapping
	public ResponseEntity<ProductDTO.Response> createProduct(@RequestBody ProductDTO.CreateProduct createProduct) {
		URI uri = URI.create("http://localhost:8080/products");
		return ResponseEntity.created(uri).body(productService.createProduct(createProduct));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ProductDTO.Response> updateProduct(@PathVariable("id") int id,
		@RequestBody ProductDTO.UpdateProduct updateProduct) {
		return ResponseEntity.ok(productService.updateProduct(id, updateProduct));
	}
}
