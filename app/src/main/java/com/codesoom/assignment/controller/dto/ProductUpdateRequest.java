package com.codesoom.assignment.controller.dto;

/**
 * 상품 수정 요청.
 */
public interface ProductUpdateRequest {

    String getName();

    String getMaker();

    int getPrice();

    String getImageUrl();
}
