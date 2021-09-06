package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * 상품 저장소.
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

}
