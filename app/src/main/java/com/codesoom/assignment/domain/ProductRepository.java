package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    /**
     * 상품 전체목록을 조회하고 리턴한다.
     * @return 상품 전체 목록
     */
    List<Product> findAll();

    /**
     * 상품 ID로 상품을 조회하고 해당 상품을 리턴한다.
     * @param id 상품 ID
     * @return 검색된 상품
     */
    Optional<Product> findById(Long id);

    /**
     * 새로운 상품을 추가하고 추가된 상품을 리턴한다.
     * @param product 새로운 상품 정보
     * @return 추가된 상품
     */
    Product save(Product product);

    /**
     * 특정 상품을 삭제한다.
     * @param product 삭제할 상품정보
     */
    void delete(Product product);

    void deleteAll();
}
