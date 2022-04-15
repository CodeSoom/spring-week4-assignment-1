package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

import java.math.BigDecimal;

/**
 * 고객이 입력한 정보를 엔티티로 변환하는 역할을 담당합니다.
 */
public interface ProductSaveRequest {

    String getName();

    String getMaker();

    BigDecimal getPrice();

    String getImage();

    default Product product() {
        return Product.builder()
                .name(getName())
                .maker(getMaker())
                .price(getPrice())
                .image(getImage())
                .build();
    }

}
