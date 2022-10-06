package com.codesoom.assignment.controller.dto;

/**
 * 상품을 수정하는 요청시 사용합니다
 */
public interface ProductUpdateRequest {

    String getName();

    String getMaker();

    int getPrice();

    String getImageUrl();
}
