package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductInfo {
    private final Long id;

    private final String name;

    private final String maker;

    private final Long price;

    private final String imageUrl;

    public ProductInfo(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }
}
