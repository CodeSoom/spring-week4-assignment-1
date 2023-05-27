package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

	@GetMapping("")
	public List<Product> getAll() {
		return null;
	}

	@GetMapping("/{productId}")
	public Product getById(@PathVariable long productId) {
		return new Product();
	}

	@PostMapping()
	public Product create(@RequestBody Product product) {
		return product;
	}

	@PatchMapping("/{productId}")
	public Product update(@PathVariable long productId, @RequestBody Product product) {
		return product;
	}


	@DeleteMapping("/{productId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long productId) {

	}
}
