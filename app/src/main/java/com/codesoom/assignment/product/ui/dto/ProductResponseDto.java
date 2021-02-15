package com.codesoom.assignment.product.ui.dto;

import lombok.Data;

@Data
public class ProductResponseDto {

    private Long id;

    private String name;

    private String maker;

    private int price;

    private String imageUrl;

    public ProductResponseDto(Long id, String name, String maker, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
