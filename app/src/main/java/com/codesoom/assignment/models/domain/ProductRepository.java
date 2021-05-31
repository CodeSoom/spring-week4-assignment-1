package com.codesoom.assignment.models.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 상품에 관한 순수 데이터 처리를 담당합니다.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 모든 상품을 조회합니다.
     * @return 조회된 상품 목록을 반환합니다.
     */
    List<Product> findAll();

    /**
     * 주어진 ID에 해당하는 상품을 조회합니다.
     * @param id 조회할 상품 ID
     * @return 조회된 상품을 반합니다.
     */
    Optional<Product> findProductById(Long id);

}
