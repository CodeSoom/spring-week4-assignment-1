package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("")
	public List<Product> getAll() {
		return productService.getAll();
	}

	@GetMapping("/{productId}")
	public Product getProduct(@PathVariable long productId) {
		return productService.getProduct(productId);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody Product product) {
		return productService.createProduct(product);
	}

	@PatchMapping("/{productId}")
	public Product update(@PathVariable long productId, @RequestBody Product product) {
		return productService.updateProduct(productId, product);
	}


	@DeleteMapping("/{productId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long productId) {
		productService.deleteProduct(productId);
	}
}
