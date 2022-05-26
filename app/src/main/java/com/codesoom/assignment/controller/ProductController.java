package com.codesoom.assignment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.service.ProductService;

@RestController
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	public ResponseEntity<List<ProductDTO.Response>> getProducts() {
		return ResponseEntity.ok(productService.getProducts());
	}
}
