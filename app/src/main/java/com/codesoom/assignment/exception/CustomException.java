package com.codesoom.assignment.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

	private final String message;

	public CustomException(String message) {
		super(message);
		this.message = message;
	}

	public abstract HttpStatus getStatus();

	public String getMessage() {
		return this.message;
	}
}
