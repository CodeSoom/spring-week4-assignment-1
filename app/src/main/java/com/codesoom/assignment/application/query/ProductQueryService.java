package com.codesoom.assignment.application.query;

import com.codesoom.assignment.domain.ProductInfo;

import java.util.List;

public interface ProductQueryService {

    /**
     * 전체 상품 목록을 리턴한다
     * @return 전체 상품 목록
     */
    List<ProductInfo> getProducts();

    /**
     * 상품ID에 해당하는 상품을 리턴한다.
     * @param id 상품 ID
     * @return 검색된 상품
     */
    ProductInfo getProduct(Long id);
}

