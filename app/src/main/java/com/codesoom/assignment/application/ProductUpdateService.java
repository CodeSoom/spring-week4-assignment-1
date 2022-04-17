package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;


/**
 * 상품을 수정하는 역할만 담당합니다.
 */
public interface ProductUpdateService {

    /**
     * 식별자에 해당하는 상품을 수정합니다. 만약 상품을 찾지 못할 경우 예외를 던집니다.
     *
     * @param id 상품의 식별자
     * @param productDto 사용자가 입력한 수정 데이터
     * @throws ProductNotFoundException 식별자와 매칭되는 상품을 찾지 못할 경우
     */
    Product update(Long id, ProductDto productDto);

}
