package com.codesoom.assignment.controller;

import org.springframework.web.bind.annotation.RestController;

import com.codesoom.assignment.service.ProductService;

@RestController
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
}
