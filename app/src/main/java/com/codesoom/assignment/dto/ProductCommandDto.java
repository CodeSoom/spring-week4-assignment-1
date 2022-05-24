package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.Builder;

public class ProductCommandDto {
    private String name;
    private String maker;
    private int price;
    private String imageUrl;

    protected ProductCommandDto() {}

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
