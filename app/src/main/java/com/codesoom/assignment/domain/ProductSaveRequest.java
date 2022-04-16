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
     * 상품 인스턴스로 변환해 리턴합니다.
     */
    default Product toProduct() {
        return ProductFactory.getProduct(getName(), getMaker(), getPrice(), getImageUrl());
    }

    /**
     * 상품 등록 요청 인스턴스를 리턴합니다.
     * @param name  상품명
     * @param maker  메이커
     * @param price 가격
     * @param imageUrl 이미지 URL
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