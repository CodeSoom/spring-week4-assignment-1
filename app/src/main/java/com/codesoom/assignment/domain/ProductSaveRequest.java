package com.codesoom.assignment.domain;

/**
 * 상품 등록에 필요한 데이터를 정의하는 인터페이스 입니다.
 */
public interface ProductSaveRequest {

    String getName();

    String getMaker();

    Integer getPrice();

    String getImageUrl();

    /**
     * 상품 인스턴스로 변환해 리턴합니다.
     */
    default Product toProduct() {
        return new Product(getName(), getMaker(), getPrice(), getImageUrl());
    }
}
