package com.codesoom.assignment.dto;

/**
 * 고양이 장난감 등록에 필요한 데이터를 정의합니다.
 */
public class CatToySaveDto {

    private final String maker;

    private final int price;

    private final String imagePath;

    public CatToySaveDto(String maker, int price, String imagePath) {
        this.maker = maker;
        this.price = price;
        this.imagePath = imagePath;
    }
}
