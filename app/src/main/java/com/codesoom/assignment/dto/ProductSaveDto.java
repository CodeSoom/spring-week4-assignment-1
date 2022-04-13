package com.codesoom.assignment.dto;

import java.beans.ConstructorProperties;

/**
 * 상품 등록에 필요한 데이터를 정의합니다.
 */
public class ProductSaveDto implements ProductSaveRequest {

    private final String maker;

    private final Integer price;

    private final String imagePath;

    @ConstructorProperties({"maker", "price", "imagePath"})
    public ProductSaveDto(String maker, Integer price, String imagePath) {
        this.maker = maker;
        this.price = price;
        this.imagePath = imagePath;
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
    public String getImagePath() {
        return imagePath;
    }
}
