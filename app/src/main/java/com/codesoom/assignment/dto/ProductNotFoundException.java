package com.codesoom.assignment.dto;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id) {
        super("id " + id + "를 가지는 Product를 찾지 못했습니다.");
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }
}
