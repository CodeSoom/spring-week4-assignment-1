package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

import java.math.BigDecimal;

/**
 * 상품 정보 저장 요청.
 */
public interface ProductSaveRequest {

    String getName();

    String getMaker();

    BigDecimal getPrice();

    String getImageUrl();

    default Product product() {
        return new Product(getName(), getMaker(), getPrice(), getImageUrl());
    }

}
