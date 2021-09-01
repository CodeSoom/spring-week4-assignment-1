package com.codesoom.assignment.domain;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

/**
 * Product 리소스 데이터베이스 처리를 담당한다.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
