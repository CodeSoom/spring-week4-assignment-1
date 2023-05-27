package com.codesoom.assignment.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CustomException {

	private final HttpStatus status = HttpStatus.NOT_FOUND;

	public ProductNotFoundException(long productId) {
		super("Product not found : " + productId);
	}

	@Override
	public HttpStatus getStatus() {
		return this.status;
	}
}
