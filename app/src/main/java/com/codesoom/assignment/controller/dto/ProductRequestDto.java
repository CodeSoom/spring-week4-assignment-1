package com.codesoom.assignment.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;

/**
 * 상품 생성/수정 요청시 사용합니다.
 */
public class ProductRequestDto implements ProductUpdateRequest {

    @NotEmpty(message = "이름은 필수값 입니다")
    private final String name;

    @NotEmpty(message = "가게는 필수값 입니다")
    private final String maker;

    @NotNull(message = "가격은 필수값 입니다")
    private final Integer price;

    private final String imageUrl;

    @ConstructorProperties({"name", "maker", "price", "imageUrl"})
    public ProductRequestDto(String name, String maker, Integer price, String imageUrl) {
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
    public int getPrice() {
        return price;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }
}
