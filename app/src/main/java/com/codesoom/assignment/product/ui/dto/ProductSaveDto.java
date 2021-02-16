package com.codesoom.assignment.product.ui.dto;

import lombok.Data;

@Data
public class ProductSaveDto {
    private String name;

    private String maker;

    private int price;

    private String imageUrl;

    public ProductSaveDto(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
