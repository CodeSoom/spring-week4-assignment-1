package com.codesoom.assignment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 상품 관련 DB 처리를 담당합니다.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
