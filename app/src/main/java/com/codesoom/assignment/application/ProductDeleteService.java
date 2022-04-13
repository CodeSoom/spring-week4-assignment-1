package com.codesoom.assignment.application;


/**
 * 상품을 삭제하는 역할만 담당합니다.
 */
public interface ProductDeleteService {

    /**
     * 식별자에 해당하는 상품을 삭제합니다.
     * 상품을 찾지 못할 경우 예외를 던집니다.
     *
     * @param id 상품의 식별자
     * @throws ProductNotFoundException 식별자와 매칭되는 상품을 찾지 못할 경우
     */
    void deleteById(Long id);

}
