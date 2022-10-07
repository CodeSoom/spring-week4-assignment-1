package com.codesoom.assignment;

import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The type Product delete service.
 */
public class productDeleteService {

  @Autowired
  private ProductRepository productRepository;

  /**
   * Delete all.
   */
  public void deleteAll() {
    productRepository.deleteAll();
  }
}
