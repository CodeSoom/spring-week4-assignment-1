package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * 상품 도메인 객체의 저장소를 담당합니다.
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

}
