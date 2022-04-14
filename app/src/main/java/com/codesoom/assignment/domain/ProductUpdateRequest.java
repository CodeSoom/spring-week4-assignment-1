package com.codesoom.assignment.domain;


/**
 * 상품 변경에 필요한 데이터를 정의하는 인터페이스 입니다.
 */
public interface ProductUpdateRequest {

    String getName();

    String getMaker();

    Integer getPrice();

    String getImagePath();
}
