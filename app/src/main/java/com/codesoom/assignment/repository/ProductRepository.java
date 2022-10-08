package com.codesoom.assignment.repository;

import com.codesoom.assignment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 상품 저장소를 추상화합니다.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
