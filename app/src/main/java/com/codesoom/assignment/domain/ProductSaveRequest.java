package com.codesoom.assignment.domain;

import com.codesoom.assignment.factories.ProductFactory;

/**
 * 상품 등록 요청
 */
public interface ProductSaveRequest {

    String getName();

    String getMaker();

    Integer getPrice();

    String getImageUrl();

    /**
     * 상품 인스턴스 반환
     */
    default Product toProduct() {
        return ProductFactory.createNewProduct(getName(), getMaker(), getPrice(), getImageUrl());
    }

    /**
     * 상품 등록 요청 인스턴스 생성
     * @param name  상품명
     * @param maker  메이커
     * @param price 가격
     * @param imageUrl 이미지 URL
     * @return 상품 등록 요청 데이터
     */
    static ProductSaveRequest of(String name, String maker, int price, String imageUrl) {
        return new ProductSaveRequest() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getMaker() {
                return maker;
            }

            @Override
            public Integer getPrice() {
                return price;
            }

            @Override
            public String getImageUrl() {
                return imageUrl;
            }
        };
    }
}
