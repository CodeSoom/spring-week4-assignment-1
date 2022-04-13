package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;

// TODO - Javadoc 작성
public interface ProductSaveRequest {

    String getMaker();

    Integer getPrice();

    String getImagePath();

    /**
     *  상품 인스턴스로 변환해 리턴합니다.
     */
    default Product toProduct() {
        return new Product(getMaker(), getPrice(), getImagePath());
    }
}
