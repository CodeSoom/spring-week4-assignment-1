package com.codesoom.assignment.domain;


/**
 * 상품 변경 요청
 */
public interface ProductUpdateRequest {

    String getName();

    String getMaker();

    Integer getPrice();

    String getImageUrl();
}
