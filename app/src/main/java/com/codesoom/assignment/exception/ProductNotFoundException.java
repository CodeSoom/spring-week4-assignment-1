package com.codesoom.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 상품을 찾지 못했을 때 던집니다.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException{

  public ProductNotFoundException(String message) {
    super(message);
  }
}
