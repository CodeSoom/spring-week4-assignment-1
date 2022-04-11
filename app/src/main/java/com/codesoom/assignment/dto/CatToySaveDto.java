package com.codesoom.assignment.dto;

import java.beans.ConstructorProperties;

/**
 * 고양이 장난감 등록에 필요한 데이터를 정의합니다.
 */
public class CatToySaveDto {

    private final String maker;

    private final Integer price;

    private final String imagePath;

    @ConstructorProperties({"maker", "price", "imagePath"})
    public CatToySaveDto(String maker, Integer price, String imagePath) {
        this.maker = maker;
        this.price = price;
        this.imagePath = imagePath;
    }
}
