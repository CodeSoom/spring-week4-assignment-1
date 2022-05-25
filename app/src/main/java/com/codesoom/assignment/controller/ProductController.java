package com.codesoom.assignment.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody ProductDTO.CreateProduct CreateProduct) {
		productService.createProduct(CreateProduct);
		return ResponseEntity.created(URI.create("http://localhost:8080/products")).build();
	}

	@GetMapping
	public ResponseEntity<?> getProducts() {
		return ResponseEntity.ok(productService.getProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProduct(@PathVariable("id") int id) {
		return ResponseEntity.ok(productService.getProduct(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") int id,
		@RequestBody ProductDTO.UpdateProduct updateProduct) {
		return ResponseEntity.ok(productService.updateProduct(id, updateProduct));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> patchProduct(@PathVariable("id") int id,
		@RequestBody ProductDTO.UpdateProduct updateProduct) {
		return ResponseEntity.ok(productService.updateProduct(id, updateProduct));
	}
}
