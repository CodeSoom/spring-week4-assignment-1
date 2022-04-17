package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

import java.util.List;


/**
 * 상품을 조회하는 역할만 담당합니다.
 */
public interface ProductReadService {

    /** 모든 상품을 반환합니다. */
    List<Product> findAll();

    /**
     * 식별자로 상품을 찾아 반환합니다.
     *
     * @param id 상품의 식별자
     * @throws ProductNotFoundException 식별자와 매칭되는 상품을 찾지 못할 경우
     */
    Product findById(Long id);

}
