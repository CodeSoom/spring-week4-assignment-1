package com.codesoom.assignment.domain;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Product 리소스 데이터 처리를 담당한다.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
}
