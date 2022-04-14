package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.ProductUpdateRequest;

import java.beans.ConstructorProperties;

/**
 * 상품 수정에 필요한 데이터를 정의합니다.
 */
public class ProductUpdateDto implements ProductUpdateRequest {

    private final String name;

    private final String maker;

    private final Integer price;

    private final String imageUrl;

    @ConstructorProperties({"name", "maker", "price", "imageUrl"})
    public ProductUpdateDto(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

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
    public String getImagePath() {
        return imageUrl;
    }
}
