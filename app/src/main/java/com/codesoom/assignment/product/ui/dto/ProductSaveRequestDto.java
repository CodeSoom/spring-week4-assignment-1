package com.codesoom.assignment.product.ui.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductSaveRequestDto {
    private String name;

    private String maker;

    private int price;

    private String imageUrl;

    public ProductSaveRequestDto(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
