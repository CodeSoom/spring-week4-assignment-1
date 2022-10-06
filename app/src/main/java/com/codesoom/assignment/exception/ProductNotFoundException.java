package com.codesoom.assignment.exception;

/**
 * findById로 product를 찾지 못했을 때 발생하는 exception
 */
public class ProductNotFoundException extends RuntimeException{

  /**
   * Instantiates a new Product not found exception.
   *
   * @param message the message
   */
  public ProductNotFoundException(String message) {
    super(message);
  }
}
