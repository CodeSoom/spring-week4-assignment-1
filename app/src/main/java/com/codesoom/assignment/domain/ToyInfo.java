package com.codesoom.assignment.domain;

import lombok.Getter;

@Getter
public class ToyInfo {
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    public ToyInfo() {
    }

    public ToyInfo(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public CatToy toCatToy() {
        return new CatToy(name, maker, price, imageUrl);
    }
}
