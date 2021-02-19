package com.codesoom.assignment.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 상품을 관리하는 Repository 클래스.
 *
 * @See Product
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    /**
     * 모든 product를 반환합니다.
     *
     * @return 모든 product 리스트
     */
    @Override
    List<Product> findAll();
}
