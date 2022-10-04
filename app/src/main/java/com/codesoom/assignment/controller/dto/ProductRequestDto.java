package com.codesoom.assignment.controller.dto;

import com.codesoom.assignment.domain.Product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;

/**
 * 장난감 수정/생성 요청시 사용합니다
 */
public class ProductRequestDto {

    @NotEmpty(message = "이름은 필수값 입니다")
    private final String name;

    @NotEmpty(message = "가게는 필수값 입니다")
    private final String maker;

    @NotNull(message = "가격은 필수값 입니다")
    private final Integer price;

    @NotEmpty(message = "이미지는 필수값 입니다")
    private final String imageUrl;

    @ConstructorProperties({"name", "maker", "price", "imageUrl"})
    public ProductRequestDto(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product toEntity() {
        return new Product(this.name, this.maker, this.price, this.imageUrl);
    }
}
