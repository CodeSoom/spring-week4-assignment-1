package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class ProductCommandDto {
    private final String name;
    private final String maker;
    private final int price;
    private final String imageUrl;

    @ConstructorProperties({"name", "maker", "price", "imageUrl"})
    @Builder
    public ProductCommandDto(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toEntity() {
        return new Product(name, maker, price, imageUrl);
    }
}
