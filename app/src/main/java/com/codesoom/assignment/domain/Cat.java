package com.codesoom.assignment.domain;

import lombok.Builder;

public class Cat {
    private String name;
    private String maker;
    private Long price;
    private String imgUrl;

    public static Cat create(String name, String maker, Long price, String imgUrl) {
        return Cat.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .imgUrl(imgUrl)
                .build();
    }

    @Builder
    private Cat(String name, String maker, Long price, String imgUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public Long getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Cat update(String updateName, String updateMaker, Long updatePrice, String updateUrl) {
        this.name = updateName;
        this.maker = updateMaker;
        this.price = updatePrice;
        this.imgUrl = updateUrl;
        return this;
    }
}
