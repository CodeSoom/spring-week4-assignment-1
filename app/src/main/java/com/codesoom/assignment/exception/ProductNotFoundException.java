package com.codesoom.assignment.exception;

/**
 * The type Product not found exception.
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
