package com.codesoom.assignment.exception;

/**
 * findById로 product를 찾지 못했을 때 던지는 exception
 */
public class ProductNotFoundException extends RuntimeException{

  public ProductNotFoundException(String message) {
    super(message);
  }
}
