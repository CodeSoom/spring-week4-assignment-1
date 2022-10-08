package com.codesoom.assignment.TestService;

import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 테스트 코드에서만 사용하는 productDeleteService
 */
@Service
public class ProductDeleteService implements TestOnly {

  @Autowired
  private final ProductRepository productRepository;

  public ProductDeleteService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void deleteAll() {
    productRepository.deleteAll();
  }

}
